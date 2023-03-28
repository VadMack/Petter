package ru.gortea.petter.ui_kit.dialogs.selector

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.button.SecondaryButton

@Composable
fun<T> SelectorDialog(
    items: List<T>,
    onItemSelected: (T) -> Unit,
    itemToTextMapper: @Composable (T) -> String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items.forEach { 
                Text(
                    text = itemToTextMapper(it),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.clickable { 
                        onItemSelected(it)
                        onDismiss()
                    }
                        .padding(vertical = 6.dp)
                        .fillMaxWidth()
                )
            }
            
            SecondaryButton(
                text = stringResource(R.string.cancel),
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
