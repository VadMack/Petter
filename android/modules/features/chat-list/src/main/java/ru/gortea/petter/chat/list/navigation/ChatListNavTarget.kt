package ru.gortea.petter.chat.list.navigation

import kotlinx.parcelize.Parcelize
import ru.gortea.petter.navigation.NavTarget

sealed class ChatListNavTarget : NavTarget {

    @Parcelize
    internal object ChatList : ChatListNavTarget()

    @Parcelize
    internal class Chat(val userId: String) : ChatListNavTarget()
}
