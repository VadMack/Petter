package ru.gortea.petter.profile.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import ru.gortea.petter.navigation.PetterRouter
import ru.gortea.petter.profile.R
import ru.gortea.petter.profile.di.ProfileComponent
import ru.gortea.petter.profile.navigation.ProfileNavTarget
import ru.gortea.petter.profile.presentation.ProfileUiEvent
import ru.gortea.petter.profile.presentation.createProfileStore
import ru.gortea.petter.profile.ui.mapper.ProfileUiStateMapper
import ru.gortea.petter.profile.ui.state.ProfileUiState
import ru.gortea.petter.profile.ui.state.ProfileUserModel
import ru.gortea.petter.theme.Base600
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.theme.button2
import ru.gortea.petter.ui_kit.avatar.Avatar
import ru.gortea.petter.ui_kit.text.TextWithIcon
import ru.gortea.petter.ui_kit.toolbar.ClickableIcon
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
    val component: ProfileComponent = getComponent()

    val store by storeHolder { createProfileStore(id, component, router, finish) }

    store.collect(ProfileUiStateMapper()) { state ->
        ProfileScreen(
            canGoBack = canGoBack,
            state = state,
            backClicked = { store.dispatch(ProfileUiEvent.Back) },
            editClicked = { store.dispatch(ProfileUiEvent.EditProfile) },
            logoutClicked = { store.dispatch(ProfileUiEvent.Logout) }
        )
    }
}

@VisibleForTesting
@Composable
private fun ProfileScreen(
    canGoBack: Boolean,
    state: ProfileUiState,
    backClicked: () -> Unit,
    editClicked: () -> Unit,
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
                modifier = Modifier.padding(padding)
            )
        }
    )
}

@Composable
private fun ProfileRoot(
    state: ProfileUiState,
    modifier: Modifier
) {
    when (state.userState) {
        is DataState.Loading, DataState.Empty -> ProfileLoading(modifier)
        is DataState.Content -> ProfileContent(state = state.userState.content, modifier = modifier)
        is DataState.Fail -> Unit // TODO add error state
    }
}

@Composable
private fun ProfileLoading(
    modifier: Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ProfileContent(
    state: ProfileUserModel,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Avatar(
            image = state.avatar?.let {
                rememberAsyncImagePainter(
                    it,
                    placeholder = painterResource(ru.gortea.petter.ui_kit.R.drawable.ic_person_placeholder),
                    error = painterResource(ru.gortea.petter.ui_kit.R.drawable.ic_person_placeholder)
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
    }
}

@Composable
private fun ProfileMenu(
    editClicked: () -> Unit,
    logoutClicked: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        ClickableIcon(
            icon = UiKitR.drawable.ic_popup,
            size = 32.dp,
            onClick = { showMenu = !showMenu }
        )

        DropdownMenu(
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                onClick = editClicked
            ) {
                Text(
                    text = stringResource(R.string.profile_edit),
                    style = MaterialTheme.typography.body2
                )
            }

            DropdownMenuItem(
                onClick = logoutClicked
            ) {
                Text(
                    text = stringResource(R.string.profile_logout),
                    style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.error)
                )
            }
        }
    }
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
            backClicked = {},
            editClicked = {},
            logoutClicked = {}
        )
    }
}