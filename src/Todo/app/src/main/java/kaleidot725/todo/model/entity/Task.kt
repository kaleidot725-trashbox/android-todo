package kaleidot725.todo.model.entity

import java.util.*

data class Task(val id : String, val name : String, val checked : Boolean) {
    companion object {
        fun createID() : String = UUID.randomUUID().toString()
    }
}