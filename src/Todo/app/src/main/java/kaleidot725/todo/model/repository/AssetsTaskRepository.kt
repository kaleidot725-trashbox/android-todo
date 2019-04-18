package kaleidot725.todo.model.repository

import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.persistence.Persistence

class AssetsTaskRepository(persistence : Persistence<Task>) : TaskRepository {

    private val persistence : Persistence<Task> = persistence
    private var loaded : Boolean = false
    private lateinit var list : MutableList<Task>

    override fun load() {
        list = persistence.load().toMutableList()
        loaded = true
    }

    override fun all(): List<Task> {
        if (!loaded) {
            throw IllegalStateException("Not loading\n")
        }

        return list
    }

    override fun active(): List<Task> {
        if (!loaded) {
            throw IllegalStateException("Not loading\n")
        }

        return list.filter { !it.checked }
    }

    override fun completed(): List<Task> {
        if (!loaded) {
            throw IllegalStateException("Not loading\n")
        }

        return list.filter { it.checked }
    }

    override fun id(id: String): Task {
        if (!loaded) {
            throw IllegalStateException("Not loading\n")
        }

        return list.first { it.id == id }
    }

    override fun add(task: Task) {
        if (!loaded) {
            throw IllegalStateException("Not loading\n")
        }

        list.add(task)
        persistence.save(list)
    }

    override fun delete(task : Task) {
        if (!loaded) {
            throw IllegalStateException("Not loading\n")
        }

        list.remove(task)
        persistence.save(list)
    }

    override fun update(task: Task) {
        if (!loaded) {
            throw IllegalStateException("Not loading\n")
        }

        val deleteTask = list.first{ it.id == task.id }
        val deleteIndex = list.indexOf(deleteTask)
        list.remove(deleteTask)
        list.add(deleteIndex, task)
        persistence.save(list)
    }
}