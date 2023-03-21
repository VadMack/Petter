package ru.gortea.petter.root.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.gortea.petter.root.R
import ru.gortea.petter.root.navigation.BottomNavigationTarget

internal enum class MenuItemModel(
    val target: BottomNavigationTarget,
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int
) {
    Main(BottomNavigationTarget.Main, R.drawable.ic_search, R.string.main_tab),
    Chats(BottomNavigationTarget.Chats, R.drawable.ic_chat, R.string.chat_tab),
    Profile(BottomNavigationTarget.Profile, R.drawable.ic_person, R.string.profile_tab)
}
