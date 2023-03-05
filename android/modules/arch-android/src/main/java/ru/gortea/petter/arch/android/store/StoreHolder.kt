package ru.gortea.petter.arch.android.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.gortea.petter.arch.store.MviStore
import kotlin.reflect.KProperty

class StoreHolder<out S : MviStore<*, *, *>> constructor(
    val store: S
) : ViewModel() {

    init {
        store.attach(viewModelScope)
    }
}

operator fun <S : MviStore<*, *, *>> StoreHolder<S>.getValue(
    thisObj: Any?, property: KProperty<*>
): S = store
