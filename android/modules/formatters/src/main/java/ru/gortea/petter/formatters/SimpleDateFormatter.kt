package ru.gortea.petter.formatters

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SimpleDateFormatter : DateFormatter {

    private val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun format(date: LocalDate): String {
        return date.format(formatter)
    }
}
