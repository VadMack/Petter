package ru.gortea.petter.arch.model

class MessageBuilder<State : Any, Action : Any, Command : Any>(initialState: State) {
    var state: State = initialState
        private set
    private var commands = listOf<Command>()
    private var actions = listOf<Action>()

    fun state(update: State.() -> State) {
        this.state = state.run(update)
    }

    fun commands(vararg command: Command) {
        commands = listOf(*command)
    }

    fun actions(vararg action: Action) {
        actions = listOf(*action)
    }

    internal fun build(): Message<State, Command, Action> = Message(state, commands, actions)
}
