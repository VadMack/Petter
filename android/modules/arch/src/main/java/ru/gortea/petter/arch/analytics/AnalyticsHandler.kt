package ru.gortea.petter.arch.analytics

interface AnalyticsHandler<State: Any, Event: Any> {
    fun logEvent(state: State, event: Event)
}
