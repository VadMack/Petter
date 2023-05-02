package ru.gortea.petter.formatters

import java.time.LocalDate

interface BirthDateFormatter {

    fun format(date: LocalDate): String
}
