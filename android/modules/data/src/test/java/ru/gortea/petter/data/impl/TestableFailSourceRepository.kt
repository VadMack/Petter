package ru.gortea.petter.data.impl

import kotlinx.coroutines.delay
import ru.gortea.petter.data.SourceRepository

internal class TestableFailSourceRepository : SourceRepository<Unit>(
    source = {
        delay(100)
        throw Exception()
    }
)
