package ru.gortea.petter.data.paging

import app.cash.turbine.test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import ru.gortea.petter.data.paging.impl.page.TestableFailSecondPageRepository
import ru.gortea.petter.data.paging.impl.page.TestableFailSourcePagingRepository
import ru.gortea.petter.data.paging.impl.page.TestableSourcePagingRepository
import ru.gortea.petter.data.paging.impl.page.model.TestablePageState
import ru.gortea.petter.data.paging.model.PagingDataState

class SourcePagingRepositoryTest : FunSpec({

    test("Load 1 page") {
        val repository = TestableSourcePagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage()
            awaitItem() shouldBe PagingDataState.Initial.Loading

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0), item.content)
        }
    }

    test("Load 3 pages") {
        val repository = TestableSourcePagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage()
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage()
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage()
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0, 1, 2), item.content)
        }
    }

    test("Load 2 pages and invalidate") {
        val repository = TestableSourcePagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage()
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage()
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.invalidate(TestablePageState())
            awaitItem() shouldBe PagingDataState.Initial.Loading

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0), item.content)
        }
    }

    test("Load 2 pages and refresh") {
        val repository = TestableSourcePagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage()
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage()
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.invalidate(TestablePageState(), refresh = true)
            assertInstanceOf(PagingDataState.Paged.Refresh::class.java, awaitItem())

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0), item.content)
        }
    }

    test("Failed load first page") {
        val repository = TestableFailSourcePagingRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage()
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Initial.Fail::class.java, awaitItem())
        }
    }

    test("Failed load second page and invalidate") {
        val repository = TestableFailSecondPageRepository()

        repository.get().test {
            awaitItem() shouldBe PagingDataState.Initial.Empty

            repository.loadPage()
            awaitItem() shouldBe PagingDataState.Initial.Loading
            assertInstanceOf(PagingDataState.Paged.Content::class.java, awaitItem())

            repository.loadPage()
            assertInstanceOf(PagingDataState.Paged.Loading::class.java, awaitItem())
            assertInstanceOf(PagingDataState.Paged.Fail::class.java, awaitItem())

            repository.invalidate(TestablePageState())
            awaitItem() shouldBe PagingDataState.Initial.Loading

            val item = awaitItem()
            assertInstanceOf(PagingDataState.Paged.Content::class.java, item)
            assertEquals(listOf(0), item.content)
        }
    }
})
