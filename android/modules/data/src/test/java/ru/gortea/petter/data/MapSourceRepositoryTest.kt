package ru.gortea.petter.data

import app.cash.turbine.test
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import ru.gortea.petter.data.impl.TestableMapRepository
import ru.gortea.petter.data.model.DataState

internal class MapSourceRepositoryTest : FunSpec({

    test("Mapped value") {
        val repository = TestableMapRepository(10)

        launch {
            delay(500)
        }

        repository.get().test {
            skipItems(1)
            repository.invalidate()
            skipItems(1)

            val item = awaitItem()
            assertInstanceOf(DataState.Content::class.java, item)

            item as DataState.Content<String>
            assertFalse(item.refreshing)
            assertEquals("10", item.content)
        }
    }
})
