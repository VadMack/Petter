package ru.gortea.petter.profile.edit.navigation.commands

import ru.gortea.petter.navigation.NavCommand

sealed interface ProfileEditNavCommand : NavCommand {
    object ProfileUpdated : ProfileEditNavCommand
}
