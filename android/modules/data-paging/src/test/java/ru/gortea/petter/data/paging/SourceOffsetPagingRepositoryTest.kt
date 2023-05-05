package ru.gortea.petter.data.paging

import app.cash.turbine.test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import ru.gortea.petter.data.paging.impl.offset.TestableFail30OffsetRepository
import ru.gortea.petter.data.paging.impl.offset.TestableFailOffsetPagingRepository
import ru.gortea.petter.data.paging.impl.offset.TestableOffsetPagingRepository
import ru.gortea.petter.data.paging.impl.offset.model.TestableOffsetState
import ru.gortea.petter.data.paging.model.PagingDataState

class SourceOffsetPagingRepositoryTest : FunSpec({

    test("Load 1 page with 30 offset") {
        val repository = TestableOffsetPagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage(0)
            awaitItem() shouldBe PagingDataState.Initial.Loading

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0), item.content)
        }
    }

    test("Load 3 pages with 10 offset") {
        val repository = TestableOffsetPagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage(0)
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage(10)
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage(20)
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0, 10, 20), item.content)
        }
    }

    test("Load 2 pages and invalidate") {
        val repository = TestableOffsetPagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage(0)
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage(10)
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.invalidate(TestableOffsetState())
            awaitItem() shouldBe PagingDataState.Initial.Loading

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0), item.content)
        }
    }

    test("Load 2 pages and refresh") {

        val repository = TestableOffsetPagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage(0)
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage(10)
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.invalidate(TestableOffsetState(), refresh = true)
            assertInstanceOf(PagingDataState.Paged.Refresh::class.java, awaitItem())

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0), item.content)
        }
    }

    test("Failed load first page") {
        val repository = TestableFailOffsetPagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage(0)
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Initial.Fail::class.java, awaitItem())
        }
    }

    test("Failed load second page with 30 offset and invalidate") {
        val repository = TestableFail30OffsetRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage(0)
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage(30)
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())
            assertInstanceOf(PagingDataState.Paged.Fail::class.java, awaitItem())

            repository.invalidate(TestableOffsetState())
            awaitItem() shouldBe PagingDataState.Initial.Loading

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0), item.content)
        }
    }
})
