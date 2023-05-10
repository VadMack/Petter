package ru.gortea.petter.home.main.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.home.R
import ru.gortea.petter.home.di.HomeComponent
import ru.gortea.petter.home.main.presentation.HomeState
import ru.gortea.petter.home.main.presentation.HomeUiEvent
import ru.gortea.petter.home.main.presentation.createHomeStore
import ru.gortea.petter.home.navigation.HomeNavTarget
import ru.gortea.petter.home.navigation.commands.HomeNavCommand
import ru.gortea.petter.navigation.NavCommand
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.pet.list.model.PetListKeyModel
import ru.gortea.petter.pet.list.model.isEmpty
import ru.gortea.petter.pet.list.navigation.commands.PetListNavCommand
import ru.gortea.petter.pet.list.ui.PetList
import ru.gortea.petter.pet.navigation.commands.PetNavCommand
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.icon.ClickableIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar
import ru.gortea.petter.ui_kit.R as UiKitR

@Composable
internal fun HomeScreen(router: Router<HomeNavTarget>) {
    val command by router.commands.collectAsState()

    val component = getComponent<HomeComponent>()
    val store by storeHolder { createHomeStore(router, component) }

    store.collect { state ->
        HomeScreen(
            state = state,
            openPet = { store.dispatch(HomeUiEvent.OpenPet(it)) },
            openFilters = { store.dispatch(HomeUiEvent.OpenFilters) },
            command = command
        )
    }

    HandleCommand(
        command = command,
        acceptFilters = { store.dispatch(HomeUiEvent.AcceptFilters(it)) }
    )
}

@Composable
private fun HandleCommand(
    command: NavCommand,
    acceptFilters: (PetListKeyModel) -> Unit
) {
    LaunchedEffect(command) {
        when (command) {
            is HomeNavCommand.AcceptKeyModel -> acceptFilters(command.keyModel)
        }
    }
}

@Composable
private fun HomeScreen(
    state: HomeState,
    openPet: (String) -> Unit,
    openFilters: () -> Unit,
    command: NavCommand = NavCommand.Empty
) {
    Scaffold(
        topBar = {
            Toolbar(
                text = stringResource(R.string.pets),
                endIcon = {
                    val icon = if (state.keyModel.isEmpty()) UiKitR.drawable.ic_filter
                    else UiKitR.drawable.ic_filter_check

                    val iconTint = if (state.keyModel.isEmpty()) MaterialTheme.colors.onBackground
                    else MaterialTheme.colors.secondary

                    ClickableIcon(
                        icon = icon,
                        onClick = openFilters,
                        tint = iconTint
                    )
                }
            )
        }
    ) {
        HomeScreenContent(
            state = state,
            command = command,
            openPet = openPet,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
private fun HomeScreenContent(
    state: HomeState,
    command: NavCommand,
    openPet: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PetList(
        listKey = state.keyModel,
        openPetCard = openPet,
        command = command.toPetListNavCommand(),
        pullToRefreshEnabled = true,
        modifier = modifier.fillMaxSize()
    )
}

private fun NavCommand.toPetListNavCommand(): NavCommand {
    return when (this) {
        is PetNavCommand.PetLikedChanged -> if (liked) {
            PetListNavCommand.PetLiked(id)
        } else {
            PetListNavCommand.PetDisliked(id)
        }
        else -> NavCommand.Empty
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreen_Preview() {
    PetterAppTheme {
        HomeScreen(
            state = HomeState(),
            openPet = {},
            openFilters = {}
        )
    }
}
