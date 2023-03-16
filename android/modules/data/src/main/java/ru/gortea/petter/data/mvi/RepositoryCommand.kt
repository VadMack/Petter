package ru.gortea.petter.data.mvi

import ru.gortea.petter.data.model.Arguments

internal interface RepositoryCommand {

    class Invalidate(val args: Arguments) : RepositoryCommand
}
