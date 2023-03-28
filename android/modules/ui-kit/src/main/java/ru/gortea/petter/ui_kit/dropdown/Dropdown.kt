package ru.gortea.petter.ui_kit.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun Dropdown(
    items: List<DropdownItem>,
    target: @Composable (MutableState<Boolean>) -> Unit,
    modifier: Modifier = Modifier
) {
    val showMenu = remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        target(showMenu)

        DropdownMenu(
            modifier = modifier.clip(RoundedCornerShape(8.dp)),
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false }
        ) {
            items.forEach {
                DropdownMenuItem(
                    onClick = {
                        showMenu.value = false
                        it.onSelected()
                    }
                ) {
                    Text(
                        text = it.text,
                        style = it.style()
                    )
                }
            }
        }
    }
}
