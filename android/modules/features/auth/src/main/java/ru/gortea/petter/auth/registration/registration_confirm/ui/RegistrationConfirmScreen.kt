package ru.gortea.petter.auth.registration.registration_confirm.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.auth.registration.navigation.RegistrationRouter
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmStore
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmUiEvent.Back
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmUiEvent.CodeChanged
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmUiEvent.Confirm
import ru.gortea.petter.auth.registration.registration_confirm.presentation.RegistrationConfirmUiEvent.ResendCode
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
fun RegistrationConfirmScreen(
    email: String,
    userId: String,
    username: String,
    pwd: String,
    router: RegistrationRouter
) {
    val component: AuthorizationComponent = getComponent()
    val store: RegistrationConfirmStore by storeHolder("RegistrationConfirm") {
        createRegistrationConfirmStore(
            component = component,
            email = email,
            userId = userId,
            username = username,
            password = pwd,
            router = router
        )
    }

    store.collect(RegistrationConfirmUiStateMapper()) { state ->
        RegistrationConfirmScreen(
            state = state,
            codeChanged = { store.dispatch(CodeChanged(it)) },
            sendCodeClicked = { store.dispatch(Confirm) },
            resendCodeClicked = { store.dispatch(ResendCode) },
            backClicked = { store.dispatch(Back) }
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
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.77f))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.appHeader,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(0.55f))

        RegistrationConfirmDescription(state, codeChanged, sendCodeClicked, resendCodeClicked)

        Spacer(modifier = Modifier.weight(1.68f))
    }
}

@Composable
private fun RegistrationConfirmDescription(
    state: RegistrationConfirmUiState,
    codeChanged: (String) -> Unit,
    sendCodeClicked: () -> Unit,
    resendCodeClicked: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
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
