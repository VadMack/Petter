package ru.gortea.petter.home.presentation

internal sealed interface HomeUiEvent {

    class OpenPet(val id: String) : HomeUiEvent
}
