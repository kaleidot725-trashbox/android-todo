package kaleidot725.todo.main

import android.app.Application
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.repository.TaskRepository

class MainViewModel(application : Application, navigator : MainNavigator, taskRepository: TaskRepository) : ViewModel() {
    val taskViewModels : MutableLiveData<List<TaskViewModel>> = MutableLiveData()

    private val application : Application = application
    private val navigator : MainNavigator = navigator
    private val taskRepository : TaskRepository = taskRepository
    private val changedCallback = object : ObservableList.OnListChangedCallback<ObservableList<Task>>() {
        override fun onItemRangeInserted(sender: ObservableList<Task>?, positionStart: Int, itemCount: Int)
        {
            if(sender != null) {
                updateTaskViewModels(sender)
            }
        }

        override fun onItemRangeRemoved(sender: ObservableList<Task>?, positionStart: Int, itemCount: Int)
        {
            if(sender != null) {
                updateTaskViewModels(sender)
            }
        }

        override fun onChanged(sender: ObservableList<Task>?)
        {
            if(sender != null) {
                updateTaskViewModels(sender)
            }
        }

        override fun onItemRangeMoved(sender: ObservableList<Task>?, fromPosition: Int, toPosition: Int, itemCount: Int) { }
        override fun onItemRangeChanged(sender: ObservableList<Task>?, positionStart: Int, itemCount: Int) { }
    }

    init {
        createTaskViewModels(taskRepository.all())
        taskRepository.addOnChangedCallback(changedCallback)
    }

    override fun onCleared() {
        taskRepository.removeOnChangedCallback(changedCallback)
        super.onCleared()
    }

    private fun createTaskViewModels(tasks : List<Task>) {
        val vms = mutableListOf<TaskViewModel>()
        for (task in tasks) {
            vms.add(TaskViewModelFactory(application, navigator, taskRepository, task).create(TaskViewModel::class.java))
        }
        taskViewModels.value = vms
    }

    private fun updateTaskViewModels(tasks : List<Task>) {
        val vms = mutableListOf<TaskViewModel>()
        for (task in tasks) {
            vms.add(TaskViewModelFactory(application, navigator, taskRepository, task).create(TaskViewModel::class.java))
        }
        taskViewModels.postValue(vms)
    }
}