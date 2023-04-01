package ru.gortea.petter.data.paging.mvi

import ru.gortea.petter.data.paging.model.PageState

internal sealed interface PagingCommand {

    class LoadPage<T: PageState>(val args: T) : PagingCommand
}
