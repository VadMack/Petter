package ru.gortea.petter.navigation

interface CommandController : CommandConsumer {
    fun sendCommand(command: NavCommand)
}
