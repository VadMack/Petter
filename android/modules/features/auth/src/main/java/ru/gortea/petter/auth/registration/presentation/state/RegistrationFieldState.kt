package ru.gortea.petter.auth.registration.presentation.state

internal data class RegistrationFieldState(
    val text: String = "",
    val isValid: Boolean = true,
    val isVisible: Boolean = true
)

internal fun RegistrationFieldState.isNotBlank(): Boolean = text.isNotBlank()

internal fun RegistrationFieldState.hide(): RegistrationFieldState {
    return copy(isVisible = false)
}
