package ru.gortea.petter.formatters

import java.time.LocalDate

interface DateFormatter {

    fun format(date: LocalDate): String
}
