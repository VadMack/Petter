package ru.gortea.petter.pet.navigation.commands

import ru.gortea.petter.navigation.NavCommand

sealed interface PetNavCommand : NavCommand {
    object PetUpdated : NavCommand
}
