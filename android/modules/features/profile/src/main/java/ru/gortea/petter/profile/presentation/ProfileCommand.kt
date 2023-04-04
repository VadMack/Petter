package ru.gortea.petter.profile.presentation

internal sealed interface ProfileCommand {
    class LoadUser(val id: String, val forceRemote: Boolean) : ProfileCommand
    class IsCurrentUser(val id: String) : ProfileCommand

    object InitLoadUser : ProfileCommand
}
