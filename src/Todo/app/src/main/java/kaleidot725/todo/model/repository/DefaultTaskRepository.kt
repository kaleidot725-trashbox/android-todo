package kaleidot725.todo.model.repository

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.persistence.Persistence

class DefaultTaskRepository(persistence : Persistence<Task>) : TaskRepository {

    private val persistence : Persistence<Task> = persistence
    private var initialized : Boolean = false
    private var list : ObservableList<Task> = ObservableArrayList<Task>()

    override fun init() {
        if (initialized) {
            return
        }

        list.addAll(persistence.load())
        initialized = true
    }

    override fun all(): List<Task> {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        return list.toList()
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

        list.add(0, task)
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

    override fun addOnChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<Task>>)
    {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        list.addOnListChangedCallback(callback)
    }

    override fun removeOnChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<Task>>)
    {
        if (!initialized) {
            throw IllegalStateException("Not initialized\n")
        }

        list.removeOnListChangedCallback(callback)
    }
}