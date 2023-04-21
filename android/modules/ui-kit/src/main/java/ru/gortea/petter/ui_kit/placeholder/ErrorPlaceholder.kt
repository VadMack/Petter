package ru.gortea.petter.ui_kit.placeholder

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.gortea.petter.theme.Base500
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.button.SecondaryTextButton
import ru.gortea.petter.ui_kit.icon.Icon

@Composable
fun ErrorPlaceholder(
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier.fillMaxSize(),
    reloadClicked: (() -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            icon = R.drawable.ic_alert_big,
            tint = Base500,
            size = 84.dp,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Text(
            text = stringResource(R.string.loading_error),
            style = MaterialTheme.typography.body1.copy(Base500)
        )

        reloadClicked?.let {
            SecondaryTextButton(
                text = stringResource(R.string.reload),
                onClick = reloadClicked
            )
        }
    }
}
