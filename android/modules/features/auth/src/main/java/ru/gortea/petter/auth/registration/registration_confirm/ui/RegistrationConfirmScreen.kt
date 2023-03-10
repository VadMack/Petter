package ru.gortea.petter.auth.registration.registration_confirm.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.auth.R
import ru.gortea.petter.auth.registration.di.RegistrationComponent
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmStore
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmUiEvent.CodeChanged
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmUiEvent.Confirm
import ru.gortea.petter.auth.registration.registration_confirm.presentation.createRegistrationConfirmStore
import ru.gortea.petter.auth.registration.registration_confirm.ui.mapper.RegistrationConfirmUiStateMapper
import ru.gortea.petter.auth.registration.registration_confirm.ui.state.RegistrationConfirmUiState
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.theme.appHeader
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.button.TextButton
import ru.gortea.petter.ui_kit.text_field.TextField
import ru.gortea.petter.ui_kit.toolbar.BackIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar

@Composable
fun RegistrationConfirmScreen() {
    val component: RegistrationComponent = getComponent()
    val store: RegistrationConfirmStore by storeHolder {
        createRegistrationConfirmStore(component, "ivan_ivanov@mail.ru", "123")
    }

    store.collect(RegistrationConfirmUiStateMapper()) { state ->
        RegistrationConfirmScreen(
            state = state,
            codeChanged = { store.dispatch(CodeChanged(it)) },
            sendCodeClicked = { store.dispatch(Confirm) },
            resendCodeClicked = { /* TODO Resend Code */ },
            backClicked = { /* TODO Go back */ }
        )
    }
}

@VisibleForTesting
@Composable
private fun RegistrationConfirmScreen(
    state: RegistrationConfirmUiState,
    codeChanged: (String) -> Unit,
    sendCodeClicked: () -> Unit,
    resendCodeClicked: () -> Unit,
    backClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            Toolbar(
                startIcon = { BackIcon(backClicked) },
                text = stringResource(R.string.registration)
            )
        },
        content = { padding ->
            RegistrationConfirmContent(
                state = state,
                codeChanged = codeChanged,
                sendCodeClicked = sendCodeClicked,
                resendCodeClicked = resendCodeClicked,
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            )
        }
    )
}

@Composable
private fun RegistrationConfirmContent(
    state: RegistrationConfirmUiState,
    codeChanged: (String) -> Unit,
    sendCodeClicked: () -> Unit,
    resendCodeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp, top = 112.dp),
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.appHeader,
            textAlign = TextAlign.Center
        )

        RegistrationConfirmDescription(state, codeChanged, sendCodeClicked, resendCodeClicked)
    }
}

@Composable
private fun RegistrationConfirmDescription(
    state: RegistrationConfirmUiState,
    codeChanged: (String) -> Unit,
    sendCodeClicked: () -> Unit,
    resendCodeClicked: () -> Unit
) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            text = stringResource(R.string.registration_confirm_description, state.maskedEmail),
            style = MaterialTheme.typography.body2,
        )

        RegistrationConfirmForm(state, codeChanged, sendCodeClicked, resendCodeClicked)
    }
}

@Composable
private fun RegistrationConfirmForm(
    state: RegistrationConfirmUiState,
    codeChanged: (String) -> Unit,
    sendCodeClicked: () -> Unit,
    resendCodeClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            state = state.codeState,
            onValueChange = codeChanged,
            label = stringResource(R.string.registration_confirm_code_label)
        )

        Column {
            PrimaryButton(
                state = state.sendCodeButtonState,
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.registration_confirm_send_code),
                onClick = sendCodeClicked,
                isClickable = !state.sendCodeButtonState.isLoading
            )

            TextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.registration_confirm_resend_code),
                onClick = resendCodeClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationConfirmScreen_Preview() {
    PetterAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            RegistrationConfirmScreen(
                state = RegistrationConfirmUiState(),
                codeChanged = {},
                sendCodeClicked = {},
                resendCodeClicked = {},
                backClicked = {}
            )
        }
    }
}
