package ru.gortea.petter.di

import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.plugin.Plugin

interface ComponentProviderPlugin : Plugin {

    fun<T> Node.provideComponent(): T {
        return if (parent is ComponentProviderPlugin) {
            (parent as ComponentProviderPlugin).run {
                parent?.provideComponent()
            } ?: error("ParentNode is not initialized")
        } else {
            error("ParentNode has no component")
        }
    }
}
