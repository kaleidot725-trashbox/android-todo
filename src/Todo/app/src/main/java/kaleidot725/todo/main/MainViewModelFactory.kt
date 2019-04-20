package kaleidot725.todo.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kaleidot725.todo.model.repository.TaskRepository
import java.lang.Exception

class MainViewModelFactory(
    val application: Application,
    val navigator : MainNavigator,
    val repository : TaskRepository
)
    : ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == kaleidot725.todo.main.MainViewModel::class.java) {
            return (MainViewModel(application, navigator, repository) as T)
        }

        throw Exception("Invalid Model Class")
    }
}