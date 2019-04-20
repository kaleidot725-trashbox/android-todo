package kaleidot725.todo.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.repository.TaskRepository
import java.lang.Exception

class TaskViewModelFactory(
    application: Application,
    val navigator : MainNavigator,
    val repository : TaskRepository,
    val task : Task
)
    : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == TaskViewModel::class.java) {
            return TaskViewModel(navigator, repository, task) as T
        }

        throw Exception("Invalid Model Class")
    }
}