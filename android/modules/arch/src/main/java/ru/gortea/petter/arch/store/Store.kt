package ru.gortea.petter.arch.store

import kotlinx.coroutines.CoroutineScope

interface Store<in Event: Any> {
    fun dispatch(event: Event)

    fun attach(coroutineScope: CoroutineScope)
}
