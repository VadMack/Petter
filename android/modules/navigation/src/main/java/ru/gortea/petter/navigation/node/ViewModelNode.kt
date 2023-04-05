package ru.gortea.petter.navigation.node

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Destroyable

abstract class ViewModelNode(
    buildContext: BuildContext
) : Node(buildContext), Destroyable, ViewModelStoreOwner {
    private val store by lazy { ViewModelStore() }

    override val viewModelStore: ViewModelStore = store

    override fun destroy() {
        store.clear()
        finish()
    }
}
