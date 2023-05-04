package ru.gortea.petter.data.impl

import ru.gortea.petter.data.MapSourceRepository

class TestableMapRepository(
    private val sourceValue: Int
) : MapSourceRepository<Int, String>(
    source = { sourceValue },
    mapper = { sourceValue.toString() }
)
