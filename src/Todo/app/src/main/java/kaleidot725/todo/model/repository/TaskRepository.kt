package kaleidot725.todo.model.repository

import androidx.databinding.ObservableList
import kaleidot725.todo.model.entity.Task

interface TaskRepository {
    fun init()

    fun all() : List<Task>
    fun active() : List<Task>
    fun completed() : List<Task>
    fun id(id : String) : Task

    fun add(task : Task)
    fun delete(task : Task)
    fun update(task : Task)

    fun addOnChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<Task>>)
    fun removeOnChangedCallback(callback : ObservableList.OnListChangedCallback<ObservableList<Task>>)
}