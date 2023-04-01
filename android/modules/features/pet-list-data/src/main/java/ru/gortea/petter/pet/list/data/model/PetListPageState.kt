package ru.gortea.petter.pet.list.data.model

import ru.gortea.petter.data.paging.model.PageState

data class PetListPageState internal constructor(
    override val page: Int = 0,
    override val pageSize: Int = PAGE_SIZE,
    internal val petListKey: PetListKey = PetListKey()
) : PageState(page, pageSize) {

    private companion object {
        private const val PAGE_SIZE = 1
    }
}
