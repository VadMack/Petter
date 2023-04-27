package ru.gortea.petter.arch.model

class MessageBuilder<State : Any, Command : Any>(initialState: State) {
    var state: State = initialState
        private set
    private var commands = listOf<Command>()

    fun state(update: State.() -> State) {
        this.state = state.run(update)
    }

    fun commands(vararg command: Command) {
        commands = listOf(*command)
    }

    internal fun build(): Message<State, Command> = Message(state, commands)
}
