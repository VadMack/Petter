package ru.gortea.petter.pet.navigation.commands

import ru.gortea.petter.navigation.NavCommand

sealed interface PetNavCommand : NavCommand {
    object PetUpdated : NavCommand
    class PetLikedChanged(val id: String, val liked: Boolean) : NavCommand
}
