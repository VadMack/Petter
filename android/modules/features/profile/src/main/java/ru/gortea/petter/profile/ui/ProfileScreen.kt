package ru.gortea.petter.profile.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.navigation.NavCommand
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.pet.list.model.PetListKeyModel
import ru.gortea.petter.pet.list.navigation.commands.PetListNavCommand
import ru.gortea.petter.pet.list.ui.PetList
import ru.gortea.petter.pet.navigation.commands.PetNavCommand
import ru.gortea.petter.profile.R
import ru.gortea.petter.profile.di.ProfileComponent
import ru.gortea.petter.profile.edit.navigation.commands.ProfileEditNavCommand
import ru.gortea.petter.profile.navigation.ProfileNavTarget
import ru.gortea.petter.profile.presentation.PetsListState
import ru.gortea.petter.profile.presentation.ProfileUiEvent
import ru.gortea.petter.profile.presentation.createProfileStore
import ru.gortea.petter.profile.ui.mapper.ProfileUiStateMapper
import ru.gortea.petter.profile.ui.state.ProfileUiModel
import ru.gortea.petter.profile.ui.state.ProfileUiState
import ru.gortea.petter.theme.Base600
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.theme.button2
import ru.gortea.petter.ui_kit.avatar.Avatar
import ru.gortea.petter.ui_kit.button.Fab
import ru.gortea.petter.ui_kit.dropdown.Dropdown
import ru.gortea.petter.ui_kit.dropdown.DropdownItem
import ru.gortea.petter.ui_kit.icon.ClickableIcon
import ru.gortea.petter.ui_kit.icon.Icon
import ru.gortea.petter.ui_kit.placeholder.LoadingPlaceholder
import ru.gortea.petter.ui_kit.text.TextWithIcon
import ru.gortea.petter.ui_kit.toolbar.CloseIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar
import ru.gortea.petter.ui_kit.R as UiKitR

@Composable
internal fun ProfileScreen(
    id: String,
    canGoBack: Boolean,
    router: PetterRouter<ProfileNavTarget>,
    finish: () -> Unit
) {
    val command by router.commands.collectAsState()
    val component: ProfileComponent = getComponent()

    val store by storeHolder { createProfileStore(id, component, router, finish) }

    store.collect(ProfileUiStateMapper()) { state ->
        ProfileScreen(
            canGoBack = canGoBack,
            state = state,
            command = command,
            backClicked = { store.dispatch(ProfileUiEvent.Back) },
            myListClicked = { store.dispatch(ProfileUiEvent.OpenMyPets) },
            favouritesClicked = { store.dispatch(ProfileUiEvent.OpenFavourites) },
            editClicked = { store.dispatch(ProfileUiEvent.EditProfile) },
            logoutClicked = { store.dispatch(ProfileUiEvent.Logout) },
            addPetClicked = { store.dispatch(ProfileUiEvent.AddPet) },
            openPetClicked = { store.dispatch(ProfileUiEvent.OpenPet(it)) }
        )
    }

    LaunchedEffect(command) {
        when (command) {
            is ProfileEditNavCommand.ProfileUpdated -> {
                store.dispatch(ProfileUiEvent.LoadUser(id))
                router.sendCommand(NavCommand.Empty)
            }
        }
    }
}

@VisibleForTesting
@Composable
private fun ProfileScreen(
    canGoBack: Boolean,
    state: ProfileUiState,
    command: NavCommand,
    backClicked: () -> Unit,
    myListClicked: () -> Unit,
    favouritesClicked: () -> Unit,
    editClicked: () -> Unit,
    addPetClicked: () -> Unit,
    openPetClicked: (String) -> Unit,
    logoutClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                startIcon = { if (canGoBack) CloseIcon(backClicked) },
                endIcon = {
                    if (state.hasProfileMenu) {
                        ProfileMenu(
                            editClicked = editClicked,
                            logoutClicked = logoutClicked
                        )
                    }
                }
            )
        },
        content = { padding ->
            ProfileRoot(
                state = state,
                command = command,
                myListClicked = myListClicked,
                favouritesClicked = favouritesClicked,
                addPetClicked = addPetClicked,
                openPetClicked = openPetClicked,
                modifier = Modifier.padding(padding)
            )
        }
    )
}

@Composable
private fun ProfileRoot(
    state: ProfileUiState,
    command: NavCommand,
    myListClicked: () -> Unit,
    favouritesClicked: () -> Unit,
    addPetClicked: () -> Unit,
    openPetClicked: (String) -> Unit,
    modifier: Modifier
) {
    when (state.userState) {
        is DataState.Loading, DataState.Empty -> LoadingPlaceholder(modifier)
        is DataState.Content -> ProfileContent(
            state = state.userState.content,
            command = command,
            modifier = modifier,
            myListClicked = myListClicked,
            favouritesClicked = favouritesClicked,
            addPetClicked = addPetClicked,
            openPetClicked = openPetClicked
        )
        is DataState.Fail -> Unit // TODO add error state
    }
}

@Composable
private fun ProfileContent(
    state: ProfileUiModel,
    command: NavCommand,
    myListClicked: () -> Unit,
    favouritesClicked: () -> Unit,
    addPetClicked: () -> Unit,
    openPetClicked: (String) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Avatar(
                image = state.avatar?.let {
                    rememberAsyncImagePainter(
                        it,
                        placeholder = painterResource(UiKitR.drawable.ic_person_placeholder),
                        error = painterResource(UiKitR.drawable.ic_person_placeholder)
                    )
                },
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = state.name,
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
            )

            state.address?.let { address ->
                TextWithIcon(
                    text = address,
                    style = MaterialTheme.typography.button2.copy(color = Base600),
                    modifier = Modifier.padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 6.dp)
            ) {
                if (state.isFavouritesAvailable) {
                    ListSelectorMenu(
                        model = state,
                        myListClicked = myListClicked,
                        favouritesClicked = favouritesClicked
                    )
                } else {
                    Text(
                        text = stringResource(R.string.pets),
                        style = MaterialTheme.typography.h3
                    )
                }
            }

            when (state.petsListState) {
                PetsListState.MINE -> {
                    PetList(
                        listKey = PetListKeyModel(ownerId = state.id),
                        command = command.mapToPetListNavCommand(),
                        openPetCard = openPetClicked
                    )
                }
                PetsListState.FAVOURITES -> {
                    PetList(
                        listKey = PetListKeyModel(ownerId = state.id, favourites = true),
                        command = command.mapToPetListNavCommand(),
                        openPetCard = openPetClicked
                    )
                }
            }
        }

        if (state.canAddPet) {
            Fab(
                text = stringResource(R.string.add),
                leadingIcon = UiKitR.drawable.ic_plus,
                onClick = addPetClicked,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp)
            )
        }
    }
}

private fun NavCommand.mapToPetListNavCommand(): NavCommand {
    return when (this) {
        is PetNavCommand.PetUpdated -> PetListNavCommand.InvalidateList
        is PetNavCommand.PetLikedChanged -> if (liked) {
            PetListNavCommand.PetLiked(id)
        } else {
            PetListNavCommand.PetDisliked(id)
        }
        else -> NavCommand.Empty
    }
}

@Composable
private fun ListSelectorMenu(
    model: ProfileUiModel,
    myListClicked: () -> Unit,
    favouritesClicked: () -> Unit
) {
    val currentItem = when (model.petsListState) {
        PetsListState.MINE -> stringResource(R.string.my_pets)
        PetsListState.FAVOURITES -> stringResource(R.string.favourites)
    }

    val items = when (model.petsListState) {
        PetsListState.MINE -> listOf(
            DropdownItem(
                text = stringResource(R.string.favourites),
                onSelected = favouritesClicked
            )
        )
        PetsListState.FAVOURITES -> listOf(
            DropdownItem(
                text = stringResource(R.string.my_pets),
                onSelected = myListClicked
            )
        )
    }

    Dropdown(
        target = { showMenu ->
            TextWithIcon(
                text = currentItem,
                trailingIcon = {
                    if (showMenu.value) {
                        Icon(icon = UiKitR.drawable.ic_up, size = 24.dp)
                    } else {
                        Icon(icon = UiKitR.drawable.ic_down, size = 24.dp)
                    }
                },
                style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null,
                    onClick = { showMenu.value = !showMenu.value }
                )
            )
        },
        items = items
    )
}

@Composable
private fun ProfileMenu(
    editClicked: () -> Unit,
    logoutClicked: () -> Unit
) {
    Dropdown(
        target = { showMenu ->
            ClickableIcon(
                icon = UiKitR.drawable.ic_popup,
                size = 32.dp,
                onClick = { showMenu.value = !showMenu.value }
            )
        },
        items = listOf(
            DropdownItem(
                text = stringResource(R.string.profile_edit),
                onSelected = editClicked
            ),
            DropdownItem(
                text = stringResource(R.string.profile_logout),
                onSelected = logoutClicked,
                style = { MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.error) }
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreen_Preview() {
    PetterAppTheme {
        val state = ProfileUiState(
            hasProfileMenu = false,
            userState = DataState.Empty
        )
        ProfileScreen(
            canGoBack = false,
            state = state,
            command = NavCommand.Empty,
            backClicked = {},
            myListClicked = {},
            favouritesClicked = {},
            editClicked = {},
            logoutClicked = {},
            addPetClicked = {},
            openPetClicked = {}
        )
    }
}
