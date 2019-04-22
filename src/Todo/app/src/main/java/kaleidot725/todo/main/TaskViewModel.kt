package kaleidot725.todo.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.repository.TaskRepository

class TaskViewModel(navigator: MainNavigator, repository: TaskRepository, task : Task) : ViewModel() {
    private val navigator : MainNavigator = navigator
    private val repository : TaskRepository = repository
    private val task : Task = task

    val name : MutableLiveData<String> = MutableLiveData()
    val checked : MutableLiveData<Boolean> = MutableLiveData()

    init {
        name.postValue(task.name)
        checked.postValue(task.checked)
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun delete(view : View) {
        repository.delete(task)
    }

    fun check(view : View) {
        val task = Task(task.id, task.name, checked.value ?: false)
        repository.update(task)
    }

    fun rename(view : View) : Boolean {
        navigator.editTextDialog ("Edit Task Name", name.value ?: ""){
            val task = Task(task.id, it, task.checked)
            name.postValue(it)
            repository.update(task)
        }
        return true
    }
}