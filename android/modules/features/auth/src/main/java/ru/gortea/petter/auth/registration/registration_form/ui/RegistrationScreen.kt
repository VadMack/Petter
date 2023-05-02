package ru.gortea.petter.auth.registration.registration_form.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import ru.gortea.petter.arch.android.compose.ToastIfTrue
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.auth.R
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationStore
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent.Authorize
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent.Back
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent.CreateAccount
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent.EmailChanged
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent.PasswordChanged
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent.PasswordConfirmChanged
import ru.gortea.petter.auth.registration.registration_form.presentation.RegistrationUiEvent.UsernameChanged
import ru.gortea.petter.auth.registration.registration_form.presentation.createRegistrationStore
import ru.gortea.petter.auth.registration.registration_form.ui.mapper.RegistrationUiStateMapper
import ru.gortea.petter.auth.registration.registration_form.ui.state.RegistrationUiState
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.theme.appHeader
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.button.TextButton
import ru.gortea.petter.ui_kit.text_field.TextField
import ru.gortea.petter.ui_kit.toolbar.BackIcon
import ru.gortea.petter.ui_kit.toolbar.Toolbar

@Composable
internal fun RegistrationScreen(
    router: Router<AuthorizationNavTarget>
) {
    val component: AuthorizationComponent = getComponent()
    val store: RegistrationStore by storeHolder {
        createRegistrationStore(component, router)
    }

    store.collect(RegistrationUiStateMapper()) { state ->
        RegistrationScreen(
            state = state,
            emailChanged = { store.dispatch(EmailChanged(it)) },
            usernameChanged = { store.dispatch(UsernameChanged(it)) },
            passwordChanged = { store.dispatch(PasswordChanged(it)) },
            passwordConfirmChanged = { store.dispatch(PasswordConfirmChanged(it)) },
            createAccountClicked = { store.dispatch(CreateAccount) },
            authorizeClicked = { store.dispatch(Authorize) },
            backClicked = { store.dispatch(Back) }
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
    ToastIfTrue(state.needToastError, R.string.registration_error)

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
            .padding(horizontal = 16.dp)
            .fillMaxHeight(),
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

        Spacer(modifier = Modifier.weight(0.41f))

        RegistrationForm(
            state = state,
            emailChanged = emailChanged,
            usernameChanged = usernameChanged,
            passwordChanged = passwordChanged,
            passwordConfirmChanged = passwordConfirmChanged,
            createAccountClicked = createAccountClicked,
            authorizeClicked = authorizeClicked
        )

        Spacer(modifier = Modifier.weight(0.76f))
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
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
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
            label = stringResource(R.string.username_label)
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
