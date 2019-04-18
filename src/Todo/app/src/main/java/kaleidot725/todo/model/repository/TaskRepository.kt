package kaleidot725.todo.model.repository

import kaleidot725.todo.model.entity.Task

interface TaskRepository {
    fun load()

    fun all() : List<Task>
    fun active() : List<Task>
    fun completed() : List<Task>
    fun id(id : String) : Task

    fun add(task : Task)
    fun delete(task : Task)
    fun update(task : Task)
}