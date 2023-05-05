package ru.gortea.petter.data.impl

import kotlinx.coroutines.delay
import ru.gortea.petter.data.MapSourceRepository

internal class TestableMapRepository(
    private val sourceValue: Int
) : MapSourceRepository<Int, String>(
    source = {
        delay(100)
        sourceValue
    },
    mapper = { sourceValue.toString() }
)
