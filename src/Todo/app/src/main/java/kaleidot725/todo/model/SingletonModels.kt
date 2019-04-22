package kaleidot725.todo.model

import android.app.Application
import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.persistence.JsonPersistence
import kaleidot725.todo.model.repository.DefaultTaskRepository
import kaleidot725.todo.model.repository.TaskRepository
import java.net.CacheResponse

object SingletonModels{

    lateinit var taskResposiory : TaskRepository

    fun init(application: Application) {
        taskResposiory = DefaultTaskRepository(JsonPersistence<Task>(application.filesDir.path + "task.json", Task::class.java))
        taskResposiory.init()
    }
}