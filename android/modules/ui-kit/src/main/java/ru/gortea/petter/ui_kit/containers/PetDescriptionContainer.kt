package ru.gortea.petter.ui_kit.containers

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.icon.ClickableIcon

@Composable
fun PetDescriptionContainer(
    title: String,
    isValid: Boolean = true,
    canDelete: Boolean = false,
    deleteClicked: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val color = if (isValid) MaterialTheme.colors.primary else MaterialTheme.colors.error
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, color, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h4.copy(color = color),
                modifier = Modifier.weight(1f)
            )

            if (canDelete) {
                ClickableIcon(
                    icon = R.drawable.ic_delete,
                    onClick = deleteClicked,
                    tint = MaterialTheme.colors.error
                )
            }
        }

        content()
    }
}
