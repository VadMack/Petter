package ru.gortea.petter.pet.list.navigation.commands

import ru.gortea.petter.navigation.NavCommand

sealed interface PetListNavCommand : NavCommand {
    object InvalidateList : PetListNavCommand
}
