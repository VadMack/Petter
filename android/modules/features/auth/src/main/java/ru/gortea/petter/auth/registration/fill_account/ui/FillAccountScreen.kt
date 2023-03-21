package ru.gortea.petter.auth.registration.fill_account.ui

import android.annotation.SuppressLint
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
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.auth.R
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountAction
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountStore
import ru.gortea.petter.auth.registration.fill_account.presentation.FillAccountUiEvent
import ru.gortea.petter.auth.registration.fill_account.presentation.createFillAccountStore
import ru.gortea.petter.auth.registration.fill_account.ui.mapper.FillAccountUiStateMapper
import ru.gortea.petter.auth.registration.fill_account.ui.state.FillAccountUiState
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.ui_kit.avatar.EditAvatar
import ru.gortea.petter.ui_kit.bottom_sheet.BottomSheetItem
import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.separator.Separator
import ru.gortea.petter.ui_kit.text_field.TextField
import ru.gortea.petter.ui_kit.text_field.TextFieldState
import ru.gortea.petter.ui_kit.toolbar.Toolbar

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FillAccountScreen() {
    val component: AuthorizationComponent = getComponent()
    val store: FillAccountStore by storeHolder(
        "FillAccountScreen"
    ) { createFillAccountStore(component) }

    val coroutineScope = rememberCoroutineScope()
    val modalState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val launcher = rememberLauncherForActivityResult(PickVisualMedia()) { avatar ->
        avatar?.let { store.dispatch(FillAccountUiEvent.AvatarChanged(it)) }
    }

    store.collect(
        stateMapper = FillAccountUiStateMapper(),
        stateRender = { state ->
            FillAccountScreen(
                modalState = modalState,
                state = state,
                nameChanged = { store.dispatch(FillAccountUiEvent.NameChanged(it)) },
                countryChanged = { store.dispatch(FillAccountUiEvent.CountryChanged(it)) },
                cityChanged = { store.dispatch(FillAccountUiEvent.CityChanged(it)) },
                streetChanged = { store.dispatch(FillAccountUiEvent.StreetChanged(it)) },
                houseChanged = { store.dispatch(FillAccountUiEvent.HouseChanged(it)) },
                avatarClicked = { store.dispatch(FillAccountUiEvent.AvatarClicked) },
                editAvatarClicked = { store.dispatch(FillAccountUiEvent.AvatarEditClicked) },
                deleteAvatarClicked = {
                    store.dispatch(FillAccountUiEvent.AvatarDeleteClicked)
                    coroutineScope.launch { modalState.animateTo(ModalBottomSheetValue.Hidden) }
                },
                saveClicked = { store.dispatch(FillAccountUiEvent.UpdateAccount) }
            )
        },
        actionHandler = { action ->
            coroutineScope.launch {
                when (action) {
                    FillAccountAction.ShowImageChooser -> {
                        modalState.animateTo(ModalBottomSheetValue.Expanded)
                    }
                    FillAccountAction.ShowImagePicker -> {
                        launcher.launch(PickVisualMediaRequest(ImageOnly))
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@VisibleForTesting
@Composable
private fun FillAccountScreen(
    modalState: ModalBottomSheetState,
    state: FillAccountUiState,
    nameChanged: (String) -> Unit,
    countryChanged: (String) -> Unit,
    cityChanged: (String) -> Unit,
    streetChanged: (String) -> Unit,
    houseChanged: (String) -> Unit,
    avatarClicked: () -> Unit,
    editAvatarClicked: () -> Unit,
    deleteAvatarClicked: () -> Unit,
    saveClicked: () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = modalState,
        sheetContent = {
            BottomSheetImageChooser(
                editClicked = editAvatarClicked,
                deleteClicked = deleteAvatarClicked
            )
        },
        content = {
            FillAccountScreen(
                state = state,
                nameChanged = nameChanged,
                countryChanged = countryChanged,
                cityChanged = cityChanged,
                streetChanged = streetChanged,
                houseChanged = houseChanged,
                avatarClicked = avatarClicked,
                saveClicked = saveClicked
            )
        }
    )
}

@Composable
private fun BottomSheetImageChooser(
    editClicked: () -> Unit,
    deleteClicked: () -> Unit
) {
    BottomSheetItem(
        text = stringResource(R.string.edit_avatar),
        textColor = MaterialTheme.colors.primary,
        onClick = editClicked
    )

    Separator()

    BottomSheetItem(
        text = stringResource(R.string.delete_avatar),
        textColor = MaterialTheme.colors.error,
        onClick = deleteClicked
    )
}

@Composable
private fun FillAccountScreen(
    state: FillAccountUiState,
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
            Toolbar(text = stringResource(R.string.fill_account))
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
    state: FillAccountUiState,
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
                image = state.avatar?.let { rememberAsyncImagePainter(it) },
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
            text = stringResource(R.string.fill_account_save),
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
        val state = FillAccountUiState(
            avatar = null,
            name = TextFieldState(),
            country = TextFieldState(),
            city = TextFieldState(),
            street = TextFieldState(),
            house = TextFieldState(),
            saveButton = ButtonState()
        )

        FillAccountScreen(
            modalState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
            state = state,
            nameChanged = {},
            countryChanged = {},
            cityChanged = {},
            streetChanged = {},
            houseChanged = {},
            editAvatarClicked = {},
            deleteAvatarClicked = {},
            avatarClicked = {},
            saveClicked = {}
        )
    }
}
