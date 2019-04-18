package kaleidot725.todo.model.entity

import java.util.*

object TaskFactory {
    fun createTask(name : String) : Task {
        val uuid = UUID.randomUUID().toString()
        return Task(uuid, name, false)
    }
}