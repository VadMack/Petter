package ru.gortea.petter.data

import app.cash.turbine.test
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import ru.gortea.petter.data.impl.TestableMapRepository
import ru.gortea.petter.data.model.DataState

class MapSourceRepositoryTest : FunSpec({

    test("Mapped value") {
        val repository = TestableMapRepository(10)

        launch {
            delay(1000)
            repository.invalidate()
        }

        repository.get().test {
            skipItems(2)

            val item = awaitItem()
            assert(item is DataState.Content && !item.refreshing)
            assertEquals((item as DataState.Content).content, "10")
        }
    }
})
