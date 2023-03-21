package ru.gortea.petter.auth.authorization.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.gortea.petter.arch.android.compose.collect
import ru.gortea.petter.arch.android.compose.getComponent
import ru.gortea.petter.arch.android.compose.storeHolder
import ru.gortea.petter.arch.android.store.getValue
import ru.gortea.petter.auth.R
import ru.gortea.petter.auth.authorization.presentation.AuthStore
import ru.gortea.petter.auth.authorization.presentation.AuthUiEvent
import ru.gortea.petter.auth.authorization.presentation.createAuthStore
import ru.gortea.petter.auth.authorization.ui.mapper.AuthUiStateMapper
import ru.gortea.petter.auth.authorization.ui.state.AuthUiState
import ru.gortea.petter.auth.di.AuthorizationComponent
import ru.gortea.petter.auth.navigation.AuthorizationNavTarget
import ru.gortea.petter.navigation.Router
import ru.gortea.petter.theme.Base700
import ru.gortea.petter.theme.PetterAppTheme
import ru.gortea.petter.theme.appHeader
import ru.gortea.petter.ui_kit.button.ButtonState
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.button.SecondaryTextButton
import ru.gortea.petter.ui_kit.text_field.TextField
import ru.gortea.petter.ui_kit.text_field.TextFieldState
import ru.gortea.petter.ui_kit.text_field.hideText
import ru.gortea.petter.ui_kit.R as UiKitR

@Composable
internal fun AuthorizationScreen(
    router: Router<AuthorizationNavTarget>,
    finish: () -> Unit
) {
    val component: AuthorizationComponent = getComponent()
    val store: AuthStore by storeHolder(key = "AuthorizationScreen") {
        createAuthStore(component, router, finish)
    }

    store.collect(stateMapper = AuthUiStateMapper()) { state ->
        AuthorizationScreen(
            state = state,
            usernameChanged = { store.dispatch(AuthUiEvent.UsernameChanged(it)) },
            passwordChanged = { store.dispatch(AuthUiEvent.PasswordChanged(it)) },
            authClick = { store.dispatch(AuthUiEvent.Authorize)},
            registrationClick = { store.dispatch(AuthUiEvent.Registration) }
        )
    }
}

@Composable
private fun AuthorizationScreen(
    state: AuthUiState,
    usernameChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    authClick: () -> Unit,
    registrationClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.9f))

        Image(
            painter = painterResource(UiKitR.drawable.logo),
            contentDescription = stringResource(UiKitR.string.logo_desc)
        )

        Spacer(modifier = Modifier.weight(0.49f))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.appHeader,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(0.625f))

        AuthorizationForm(
            state = state,
            usernameChanged = usernameChanged,
            passwordChanged = passwordChanged,
            authClick = authClick
        )

        Spacer(modifier = Modifier.weight(2.43f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.do_not_have_account),
                style = MaterialTheme.typography.body2.copy(color = Base700)
            )

            SecondaryTextButton(
                text = stringResource(R.string.registrate),
                onClick = registrationClick
            )
        }

        Spacer(modifier = Modifier.weight(0.56f))
    }
}

@Composable
private fun AuthorizationForm(
    state: AuthUiState,
    usernameChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit,
    authClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        CredentialsForm(
            state = state,
            usernameChanged = usernameChanged,
            passwordChanged = passwordChanged
        )

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.authorize),
            onClick = authClick,
            state = state.authorizeButton
        )
    }
}

@Composable
private fun CredentialsForm(
    state: AuthUiState,
    usernameChanged: (String) -> Unit,
    passwordChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            state = state.username,
            onValueChange = usernameChanged,
            label = stringResource(R.string.username_label)
        )

        TextField(
            state = state.password,
            onValueChange = passwordChanged,
            label = stringResource(R.string.password_label)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AuthorizationScreen_Preview() {
    PetterAppTheme {
        val state = AuthUiState(
            username = TextFieldState(),
            password = TextFieldState().hideText(),
            authorizeButton = ButtonState()
        )

        AuthorizationScreen(
            state = state,
            usernameChanged = {},
            passwordChanged = {},
            authClick = {},
            registrationClick = {}
        )
    }
}
