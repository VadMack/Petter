package ru.gortea.petter.di.formatters

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.gortea.petter.formatters.AgeDateFormatter
import ru.gortea.petter.formatters.BirthDateFormatter
import ru.gortea.petter.formatters.SimpleDateFormatter

@Module
class FormattersModule {

    @Provides
    fun provideDateFormatter(context: Context): BirthDateFormatter {
        return AgeDateFormatter(context.resources)
    }

    @Provides
    fun provideSimpleDateFormatter(): SimpleDateFormatter {
        return SimpleDateFormatter()
    }
}
