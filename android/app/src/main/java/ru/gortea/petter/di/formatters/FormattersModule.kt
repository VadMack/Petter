package ru.gortea.petter.di.formatters

import dagger.Module
import dagger.Provides
import ru.gortea.petter.formatters.DateFormatter
import ru.gortea.petter.formatters.SimpleDateFormatter

@Module
class FormattersModule {

    @Provides
    fun provideDatFormatter(): DateFormatter {
        return SimpleDateFormatter()
    }
}
