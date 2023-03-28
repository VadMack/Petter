package ru.gortea.petter.profile.edit.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ru.gortea.petter.profile.edit.R
import ru.gortea.petter.profile.edit.di.ProfileEditComponent
import ru.gortea.petter.profile.edit.presentation.ProfileEditStore
import ru.gortea.petter.profile.edit.presentation.ProfileEditUiEvent
import ru.gortea.petter.profile.edit.presentation.createProfileEditStore
import ru.gortea.petter.profile.edit.ui.mapper.ProfileEditUiStateMapper
import ru.gortea.petter.profile.edit.ui.state.ProfileEditUiState
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.avatar.EditAvatar
import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.dialogs.bottom_sheet.BottomSheetDialog
import ru.gortea.petter.ui_kit.dialogs.bottom_sheet.BottomSheetItem
import ru.gortea.petter.ui_kit.text_field.TextField
import ru.gortea.petter.ui_kit.text_field.TextFieldState
import ru.gortea.petter.ui_kit.toolbar.CloseIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar

@Composable
fun ProfileEditScreen(isProfileCreate: Boolean, finish: () -> Unit) {
    var launcherStore: ProfileEditStore? = null
    val launcher = rememberLauncherForActivityResult(PickVisualMedia()) { avatar ->
        avatar?.let { launcherStore?.dispatch(ProfileEditUiEvent.AvatarChanged(it)) }
    }
    var showModal by remember { mutableStateOf(false) }

    val component: ProfileEditComponent = getComponent()
    val store: ProfileEditStore by storeHolder {
        createProfileEditStore(
            component = component,
            showModalImageChooser = { showModal = true },
            showImagePicker = {
                launcher.launch(PickVisualMediaRequest(ImageOnly))
            },
            finish = finish
        )
    }

    launcherStore = store

    store.collect(ProfileEditUiStateMapper()) { state ->
        ProfileEditScreen(
            isProfileCreate = isProfileCreate,
            state = state,
            backClicked = { finish() },
            nameChanged = { store.dispatch(ProfileEditUiEvent.NameChanged(it)) },
            countryChanged = { store.dispatch(ProfileEditUiEvent.CountryChanged(it)) },
            cityChanged = { store.dispatch(ProfileEditUiEvent.CityChanged(it)) },
            streetChanged = { store.dispatch(ProfileEditUiEvent.StreetChanged(it)) },
            houseChanged = { store.dispatch(ProfileEditUiEvent.HouseChanged(it)) },
            avatarClicked = { store.dispatch(ProfileEditUiEvent.AvatarClicked) },
            saveClicked = { store.dispatch(ProfileEditUiEvent.UpdateAccount) }
        )

        if (showModal) {
            BottomSheetDialog(
                items = listOf(
                    BottomSheetItem(
                        text = stringResource(R.string.edit_avatar),
                        style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary),
                        onClick = { store.dispatch(ProfileEditUiEvent.AvatarEditClicked) }
                    ),
                    BottomSheetItem(
                        text = stringResource(R.string.delete_avatar),
                        style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.error),
                        onClick = { store.dispatch(ProfileEditUiEvent.AvatarDeleteClicked) }
                    )
                ),
                onDismiss = { showModal = false }
            )
        }
    }
}

@VisibleForTesting
@Composable
private fun ProfileEditScreen(
    isProfileCreate: Boolean,
    state: ProfileEditUiState,
    backClicked: () -> Unit,
    nameChanged: (String) -> Unit,
    countryChanged: (String) -> Unit,
    cityChanged: (String) -> Unit,
    streetChanged: (String) -> Unit,
    houseChanged: (String) -> Unit,
    avatarClicked: () -> Unit,
    saveClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                startIcon = if (!isProfileCreate) {
                    { CloseIcon(backClicked) }
                } else {
                    null
                },
                text = if (isProfileCreate) {
                    stringResource(R.string.fill_account)
                } else {
                    stringResource(R.string.profile_edit)
                }
            )
        },
        content = { padding ->
            FillAccountScreenContent(
                state = state,
                nameChanged = nameChanged,
                countryChanged = countryChanged,
                cityChanged = cityChanged,
                streetChanged = streetChanged,
                houseChanged = houseChanged,
                saveClicked = saveClicked,
                avatarClicked = avatarClicked,
                modifier = Modifier
                    .padding(padding)
            )
        }
    )
}

@Composable
private fun FillAccountScreenContent(
    state: ProfileEditUiState,
    nameChanged: (String) -> Unit,
    countryChanged: (String) -> Unit,
    cityChanged: (String) -> Unit,
    streetChanged: (String) -> Unit,
    houseChanged: (String) -> Unit,
    avatarClicked: () -> Unit,
    saveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 86.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.TopCenter)
        ) {
            EditAvatar(
                image = state.avatar?.let {
                    rememberAsyncImagePainter(
                        it,
                        placeholder = painterResource(ru.gortea.petter.ui_kit.R.drawable.ic_person_placeholder),
                        error = painterResource(ru.gortea.petter.ui_kit.R.drawable.ic_person_placeholder)
                    )
                },
                onClick = avatarClicked,
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )

            TextField(
                state = state.name,
                onValueChange = nameChanged,
                label = stringResource(R.string.name_label)
            )

            TextField(
                state = state.country,
                onValueChange = countryChanged,
                label = stringResource(R.string.country_label)
            )

            TextField(
                state = state.city,
                onValueChange = cityChanged,
                label = stringResource(R.string.city_label)
            )

            TextField(
                state = state.street,
                onValueChange = streetChanged,
                label = stringResource(R.string.street_label)
            )

            TextField(
                state = state.house,
                onValueChange = houseChanged,
                label = stringResource(R.string.house_label)
            )
        }

        PrimaryButton(
            state = state.saveButton,
            text = stringResource(R.string.save),
            onClick = saveClicked,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun FillAccountScreen_Preview() {
    PetterAppTheme {
        val state = ProfileEditUiState(
            avatar = null,
            name = TextFieldState(),
            country = TextFieldState(),
            city = TextFieldState(),
            street = TextFieldState(),
            house = TextFieldState(),
            saveButton = ButtonState()
        )

        ProfileEditScreen(
            isProfileCreate = true,
            state = state,
            backClicked = {},
            nameChanged = {},
            countryChanged = {},
            cityChanged = {},
            streetChanged = {},
            houseChanged = {},
            avatarClicked = {},
            saveClicked = {}
        )
    }
}
