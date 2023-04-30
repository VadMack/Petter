package ru.gortea.petter.ui_kit.placeholder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.gortea.petter.theme.Base500
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.icon.Icon

@Composable
fun EmptyPlaceholder(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon = R.drawable.ic_paw_outline,
            tint = Base500,
            size = 90.dp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(Base500),
            textAlign = TextAlign.Center
        )
    }
}
