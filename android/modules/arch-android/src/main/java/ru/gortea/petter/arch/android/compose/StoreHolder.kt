package ru.gortea.petter.arch.android.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.gortea.petter.arch.android.store.StoreHolder
import ru.gortea.petter.arch.android.store.StoreHolderFactory
import ru.gortea.petter.arch.store.MviStore

@Composable
inline fun<S : MviStore<*, *, *>> storeHolder(crossinline factory: () -> S): StoreHolder<S> {
    return viewModel(
        factory = StoreHolderFactory {
            StoreHolder(factory())
        }
    )
}

@Composable
inline fun<S : MviStore<*, *, *>> storeHolder(
    key: String,
    crossinline factory: () -> S
): StoreHolder<S> {
    return viewModel(
        key = key,
        factory = StoreHolderFactory {
            StoreHolder(factory())
        }
    )
}
