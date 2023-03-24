package ru.gortea.petter.profile.presentation

internal sealed interface ProfileCommand {
    class LoadUser(val id: String) : ProfileCommand
    class IsCurrentUser(val id: String) : ProfileCommand

    object InitLoadUser : ProfileCommand
}
