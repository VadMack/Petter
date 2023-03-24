package ru.gortea.petter.arch.android.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.gortea.petter.arch.android.store.StoreHolder
import ru.gortea.petter.arch.android.store.StoreHolderFactory
import ru.gortea.petter.arch.store.MviStore
import java.util.*

@Composable
inline fun <reified S : MviStore<*, *, *>> getScreenKey(): String {
    return rememberSaveable { S::class.toString() + UUID.randomUUID() }
}

@Composable
inline fun <reified S : MviStore<*, *, *>> storeHolder(
    key: String = getScreenKey<S>(),
    crossinline factory: () -> S
): StoreHolder<S> {
    return viewModel(
        key = key,
        factory = StoreHolderFactory {
            StoreHolder(factory())
        }
    )
}
