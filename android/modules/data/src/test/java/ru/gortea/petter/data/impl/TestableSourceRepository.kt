package ru.gortea.petter.data.impl

import kotlinx.coroutines.delay
import ru.gortea.petter.data.SourceRepository

class TestableSourceRepository : SourceRepository<Unit>(
    source = { delay(100) }
)
