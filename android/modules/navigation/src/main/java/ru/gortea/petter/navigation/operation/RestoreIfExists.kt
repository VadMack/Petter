package ru.gortea.petter.navigation.operation

import com.bumble.appyx.core.navigation.NavElements
import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.BackStackElement
import com.bumble.appyx.navmodel.backstack.BackStackElements
import com.bumble.appyx.navmodel.backstack.activeElement
import com.bumble.appyx.navmodel.backstack.operation.BackStackOperation
import com.bumble.appyx.navmodel.backstack.operation.Push
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
internal class RestoreIfExists<T : Any>(
    private val element: @RawValue T
) : BackStackOperation<T> {

    override fun invoke(
        elements: BackStackElements<T>
    ): BackStackElements<T> {
        val existedElement = elements.find { element == it.key.navTarget }
        var newElements = elements

        if (existedElement != null) {
            val existedIdx = newElements.indexOf(existedElement)
            newElements = newElements.removeAt(existedIdx)
        }

        val elementToAdd = existedElement?.transitionTo(
            BackStack.State.ACTIVE,
            this
        ) ?: BackStackElement(
            key = NavKey(element),
            fromState = BackStack.State.CREATED,
            targetState = BackStack.State.ACTIVE,
            operation = this
        )

        return newElements.transitionTo(BackStack.State.STASHED) {
            it.targetState == BackStack.State.ACTIVE
        } + elementToAdd
    }

    private fun <T : Any> BackStackElements<T>.removeAt(index: Int): BackStackElements<T> {
        if (index == lastIndex) return subList(0, index)
        return subList(0, index) + subList(index + 1, size)
    }

    override fun isApplicable(elements: NavElements<T, BackStack.State>): Boolean =
        element != elements.activeElement

    companion object {

        fun <T : Any> init(
            element: T,
            elements: BackStackElements<T>
        ): BackStackOperation<T> {
            val existedElement = elements.find { element == it.key.navTarget }
            return if (existedElement == null) {
                Push(element)
            } else {
                RestoreIfExists(element)
            }
        }
    }
}

fun <T : Any> BackStack<T>.restoreIfExists(element: T) {
    accept(RestoreIfExists.init(element, elements.value))
}
