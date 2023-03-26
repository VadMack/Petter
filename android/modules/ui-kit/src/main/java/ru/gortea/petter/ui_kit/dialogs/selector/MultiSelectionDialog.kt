package ru.gortea.petter.ui_kit.dialogs.selector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.gortea.petter.theme.Base600
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.button.SecondaryButton

@Composable
fun <T> MultiSelectionDialog(
    items: List<T>,
    onItemsSelected: (List<T>) -> Unit,
    itemToTextMapper: @Composable (T) -> String,
    onDismiss: () -> Unit,
    acceptButtonText: String,
    title: String,
    subtitle: String
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h2
                )

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.body1.copy(color = Base600)
                )
            }

            val selected = remember { mutableStateListOf<T>() }
            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                items.forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = itemToTextMapper(item),
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.weight(1f)
                        )

                        Checkbox(
                            checked = item in selected,
                            onCheckedChange = {
                                if (it) {
                                    selected.add(item)
                                } else {
                                    selected.remove(item)
                                }
                            }
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                PrimaryButton(
                    text = acceptButtonText,
                    onClick = {
                        onItemsSelected(selected)
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                SecondaryButton(
                    text = stringResource(R.string.cancel),
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
