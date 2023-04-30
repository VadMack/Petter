package ru.gortea.petter.chat.data.messages.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm")
private val monthTimeFormatter = DateTimeFormatter.ofPattern("d MMMM, hh:mm")
private val timeFormatter = DateTimeFormatter.ofPattern("hh:mm")


fun LocalDateTime.format(): String {
    return when {
        isToday() -> format(timeFormatter)
        isThisYear() -> format(monthTimeFormatter)
        else -> format(dateTimeFormatter)
    }
}

private fun LocalDateTime.isToday(): Boolean {
    val today = LocalDateTime.now().toLocalDate()
    val date = toLocalDate()

    return today == date
}

private fun LocalDateTime.isThisYear(): Boolean {
    val thisYear = LocalDateTime.now().toLocalDate().year
    val dateYear = toLocalDate().year

    return thisYear == dateYear
}
