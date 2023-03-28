package ru.gortea.petter.ui_kit.dialogs.calendar

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.button.PrimaryButton
import ru.gortea.petter.ui_kit.button.SecondaryButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarDialog(
    dateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val selDate = remember { mutableStateOf(LocalDate.now()) }

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_date_title),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = selDate.value.format(DateTimeFormatter.ofPattern("MMM d, yyyy")),
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(12.dp))
            }

            CalendarDialogView(
                dateSelected = { selDate.value = it }
            )

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryButton(
                    text = stringResource(R.string.save),
                    onClick = {
                        dateSelected(selDate.value)
                        onDismiss()
                    }
                )

                SecondaryButton(
                    text = stringResource(R.string.cancel),
                    onClick = onDismiss
                )
            }
        }
    }
}

@Composable
private fun CalendarDialogView(
    dateSelected: (LocalDate) -> Unit
) {
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            CalendarView(ContextThemeWrapper(context, R.style.CalenderViewCustom))
        },
        update = { view ->
            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                dateSelected(
                    LocalDate
                        .now()
                        .withMonth(month + 1)
                        .withYear(year)
                        .withDayOfMonth(dayOfMonth)
                )
            }
        }
    )
}
