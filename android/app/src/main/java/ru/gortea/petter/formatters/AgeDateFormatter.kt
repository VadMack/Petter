package ru.gortea.petter.formatters

import android.content.res.Resources
import ru.gortea.petter.R
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class AgeDateFormatter(
    private val resources: Resources
) : BirthDateFormatter {

    private val now = LocalDate.now()
    private val defaultFormatter = SimpleDateFormatter()

    override fun format(date: LocalDate): String {
        val years = ChronoUnit.YEARS.between(date, now).toInt()
        val months = (ChronoUnit.MONTHS.between(date, now) % 12).toInt()

        return when {
            years > 0 -> resources.getQuantityString(R.plurals.age_years, years, years)
            months > 0 -> resources.getQuantityString(R.plurals.age_months, months, months)
            else -> defaultFormatter.format(date)
        }
    }
}
