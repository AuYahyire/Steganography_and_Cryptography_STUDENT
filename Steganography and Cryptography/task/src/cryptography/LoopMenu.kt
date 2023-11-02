package cryptography

import cryptography.tasks.HideMessageClass
import cryptography.tasks.PrintBytesClass
import cryptography.tasks.ShowMessageClass

class LoopMenu {
    fun startLoopMenu() {
        var exit = false
        do {
            println("Task (hide, show, exit):")
            val userInput = readln().lowercase()
            when (userInput) {
                "hide" -> HideMessageClass().executeTask()
                "show" -> ShowMessageClass().executeTask()
                "print" -> PrintBytesClass().print()
                "exit" -> exit = true
                else -> println("Wrong task: $userInput")
            }
        } while (!exit)
        println("Bye!")
    }
}