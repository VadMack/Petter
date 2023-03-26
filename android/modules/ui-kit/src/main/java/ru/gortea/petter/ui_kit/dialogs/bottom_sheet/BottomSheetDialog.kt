package ru.gortea.petter.ui_kit.dialogs.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.gortea.petter.ui_kit.separator.Separator

@Composable
fun BottomSheetDialog(
    items: List<BottomSheetItem>,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .padding(vertical = 16.dp)
            ) {
                items.forEachIndexed { i, it ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                it.onClick()
                                onDismiss()
                            }
                            .padding(vertical = 4.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = it.text,
                            style = it.style
                        )
                    }

                    if (i != items.lastIndex) {
                        Separator()
                    }
                }
            }
        }
    }
}
