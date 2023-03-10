package ru.gortea.petter.auth.registration.registration_form.ui

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
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationStore
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent.*
import ru.gortea.petter.auth.registration.registration_form.presentation.createRegistrationStore
import ru.gortea.petter.auth.registration.registration_form.ui.mapper.RegistrationUiStateMapper
import ru.gortea.petter.auth.registration.registration_form.ui.state.RegistrationUiState
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.theme.appHeader
import ru.gortea.petter.ui_kit.text_field.TextField
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.button.TextButton
import ru.gortea.petter.ui_kit.toolbar.BackIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar

/**
 * Проверить количество рекомпозиций при изменении рандомного поля в стейте
 * Настроить навигацию
 */

@Composable
fun RegistrationScreen() {
    val component: RegistrationComponent = getComponent()
    val store: RegistrationStore by storeHolder { createRegistrationStore(component) }

    store.collect(RegistrationUiStateMapper()) { state ->
        RegistrationScreen(
            state = state,
            emailChanged = { store.dispatch(EmailChanged(it)) },
            usernameChanged = { store.dispatch(UsernameChanged(it)) },
            passwordChanged = { store.dispatch(PasswordChanged(it)) },
            passwordConfirmChanged = { store.dispatch(PasswordConfirmChanged(it)) },
            createAccountClicked = { store.dispatch(CreateAccount) },
            authorizeClicked = { /* TODO Open auth screen */ },
            backClicked = { /* TODO Go back */ }
        )
    }
}

@VisibleForTesting
@Composable
private fun RegistrationScreen(
    state: RegistrationUiState,
    emailChanged: (String) -> Unit,
    usernameChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    passwordConfirmChanged: (String) -> Unit,
    createAccountClicked: () -> Unit,
    authorizeClicked: () -> Unit,
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
            RegistrationScreenContent(
                state = state,
                emailChanged = emailChanged,
                usernameChanged = usernameChanged,
                passwordChanged = passwordChanged,
                passwordConfirmChanged = passwordConfirmChanged,
                createAccountClicked = createAccountClicked,
                authorizeClicked = authorizeClicked,
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            )
        }
    )
}

@Composable
private fun RegistrationScreenContent(
    state: RegistrationUiState,
    emailChanged: (String) -> Unit,
    usernameChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    passwordConfirmChanged: (String) -> Unit,
    createAccountClicked: () -> Unit,
    authorizeClicked: () -> Unit,
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
                .padding(bottom = 60.dp, top = 112.dp),
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.appHeader,
            textAlign = TextAlign.Center
        )

        RegistrationForm(
            state = state,
            emailChanged = emailChanged,
            usernameChanged = usernameChanged,
            passwordChanged = passwordChanged,
            passwordConfirmChanged = passwordConfirmChanged,
            createAccountClicked = createAccountClicked,
            authorizeClicked = authorizeClicked
        )
    }
}

@Composable
private fun RegistrationForm(
    state: RegistrationUiState,
    emailChanged: (String) -> Unit,
    usernameChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    passwordConfirmChanged: (String) -> Unit,
    createAccountClicked: () -> Unit,
    authorizeClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            state = state.emailState,
            placeholder = stringResource(R.string.email_placeholder),
            onValueChange = emailChanged,
            label = stringResource(R.string.email_label)
        )

        TextField(
            state = state.usernameState,
            placeholder = stringResource(R.string.username_placeholder),
            onValueChange = usernameChanged,
            label = stringResource(R.string.user_name_label)
        )

        TextField(
            state = state.passwordState,
            placeholder = stringResource(R.string.password_placeholder),
            onValueChange = passwordChanged,
            label = stringResource(R.string.password_label)
        )

        TextField(
            state = state.passwordConfirmState,
            placeholder = stringResource(R.string.password_confirm_placeholder),
            onValueChange = passwordConfirmChanged,
            label = stringResource(R.string.password_confirm_label)
        )

        Column {
            PrimaryButton(
                state = state.createAccountState,
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.create_account),
                onClick = createAccountClicked,
                isClickable = !state.createAccountState.isLoading
            )

            TextButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.authorize),
                onClick = authorizeClicked
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreen_Preview() {
    PetterAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            RegistrationScreen(
                state = RegistrationUiState(),
                emailChanged = {},
                usernameChanged = {},
                passwordChanged = {},
                passwordConfirmChanged = {},
                createAccountClicked = {},
                authorizeClicked = {},
                backClicked = {}
            )
        }
    }
}
