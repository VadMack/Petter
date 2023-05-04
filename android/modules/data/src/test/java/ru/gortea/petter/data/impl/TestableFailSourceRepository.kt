package ru.gortea.petter.data.impl

import ru.gortea.petter.data.SourceRepository

class TestableFailSourceRepository : SourceRepository<Unit>(
    source = { throw Exception() }
)
