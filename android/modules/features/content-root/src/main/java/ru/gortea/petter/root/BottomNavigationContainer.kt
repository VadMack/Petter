package ru.gortea.petter.root

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.gortea.petter.root.model.MenuItemModel
import ru.gortea.petter.root.navigation.BottomNavigationRouter
import ru.gortea.petter.ui_kit.separator.Separator

@Composable
internal fun BottomNavigationContainer(
    router: BottomNavigationRouter
) {
    val items = remember {
        listOf(
            MenuItemModel.Main,
            MenuItemModel.Chats,
            MenuItemModel.Profile
        )
    }

    Column {
        Separator(padding = 0.dp)

        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp
        ) {
            val currentItem by router.currentNodeAsState()
            val currentTarget = currentItem.lastOrNull()?.key?.navTarget

            items.forEach { item ->
                val isSelected = item.target == currentTarget
                BottomNavigationItem(
                    selected = isSelected,
                    icon = {
                        Icon(
                            painter = painterResource(item.iconRes),
                            contentDescription = stringResource(item.titleRes)
                        )
                    },
                    onClick = { router.restoreIfExists(item.target) },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.surface
                )
            }
        }
    }
}
