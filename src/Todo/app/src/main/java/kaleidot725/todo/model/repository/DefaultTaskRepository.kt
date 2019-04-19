package kaleidot725.todo.model.repository

import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.persistence.Persistence

class DefaultTaskRepository(persistence : Persistence<Task>) : TaskRepository {

    private val persistence : Persistence<Task> = persistence
    private var initialized : Boolean = false
    private lateinit var list : MutableList<Task>
    private val values : List<Task> = mutableListOf(
        Task("1", "ABCDEFG", false),
        Task("2", "HIJKLMN", true),
        Task("3", "1234567", false),
        Task("4", "0987654", true),
        Task("5", "3456789", false))

    override fun init() {
        if (initialized) {
            return
        }

        list = values.toMutableList()
        initialized = true
    }

    override fun all(): List<Task> {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        return list
    }

    override fun active(): List<Task> {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        return list.filter { !it.checked }
    }

    override fun completed(): List<Task> {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        return list.filter { it.checked }
    }

    override fun id(id: String): Task {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        return list.first { it.id == id }
    }

    override fun add(task: Task) {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        list.add(task)
        persistence.save(list)
    }

    override fun delete(task : Task) {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        list.remove(task)
        persistence.save(list)
    }

    override fun update(task: Task) {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        val deleteTask = list.first{ it.id == task.id }
        val deleteIndex = list.indexOf(deleteTask)
        list.remove(deleteTask)
        list.add(deleteIndex, task)
        persistence.save(list)
    }
}