package ru.gortea.petter.arch

import kotlinx.coroutines.flow.Flow

interface Actor<in Command : Any, out Event : Any> {
    fun process(commands: Flow<Command>): Flow<Event>
}
