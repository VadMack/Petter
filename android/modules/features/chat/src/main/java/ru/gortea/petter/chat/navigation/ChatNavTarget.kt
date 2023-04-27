package ru.gortea.petter.chat.navigation

import kotlinx.parcelize.Parcelize
import ru.gortea.petter.navigation.NavTarget

sealed class ChatNavTarget : NavTarget {

    @Parcelize
    internal object Chat : ChatNavTarget()

    @Parcelize
    internal object Profile : ChatNavTarget()
}
