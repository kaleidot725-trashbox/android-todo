package kaleidot725.todo.main

import android.app.Application
import android.view.View
import androidx.databinding.ObservableList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.repository.TaskRepository

class MainViewModel(application : Application, navigator : MainNavigator, taskRepository: TaskRepository) : ViewModel() {
    private val mutableTaskViewModels : MutableLiveData<List<TaskViewModel>> = MutableLiveData()
    private val application : Application = application
    private val navigator : MainNavigator = navigator
    private val taskRepository : TaskRepository = taskRepository
    private var currentSelectType : TaskSelectType = TaskSelectType.ALL
    private val changedCallback = object : ObservableList.OnListChangedCallback<ObservableList<Task>>() {
        override fun onItemRangeInserted(sender: ObservableList<Task>?, positionStart: Int, itemCount: Int)
        {
            if(sender != null) { update(currentSelectType) }
        }

        override fun onItemRangeRemoved(sender: ObservableList<Task>?, positionStart: Int, itemCount: Int)
        {
            if(sender != null) { update(currentSelectType) }
        }

        override fun onChanged(sender: ObservableList<Task>?)
        {
            if(sender != null) { update(currentSelectType) }
        }

        override fun onItemRangeMoved(sender: ObservableList<Task>?, fromPosition: Int, toPosition: Int, itemCount: Int) { }
        override fun onItemRangeChanged(sender: ObservableList<Task>?, positionStart: Int, itemCount: Int) { }
    }

    val taskViewModels : LiveData<List<TaskViewModel>> get() { return mutableTaskViewModels }
    val taskName : MutableLiveData<String> = MutableLiveData()
    val leftCount : MutableLiveData<String> = MutableLiveData()

    init {
        val vms = mutableListOf<TaskViewModel>()
        for (task in taskRepository.all()) {
            vms.add(TaskViewModelFactory(application, navigator, taskRepository, task).create(TaskViewModel::class.java))
        }
        mutableTaskViewModels.value = vms
        taskName.value = ""
        leftCount.value = "${taskRepository.active().count()} left item"

        onChangedEnable()
    }

    fun add(view : View) {
        onChangedDisable()

        taskRepository.add(Task(Task.createID(), taskName.value.toString(), false))
        taskName.postValue("")

        onChangedEnable()
        update(currentSelectType)
    }

    fun clearCompleted(view : View) {
        onChangedDisable()

        val completedTask = taskRepository.completed()
        for (task in completedTask)  {
            taskRepository.delete(task)
        }

        onChangedEnable()
        update(currentSelectType)
    }

    fun check(view : View) {
        onChangedDisable()

        val checkable = (0 < taskRepository.active().count())
        if (checkable) {
            val activeTasks = taskRepository.active()
            for (activeTask in activeTasks) {
                taskRepository.update(Task(activeTask.id, activeTask.name, true))
            }
        }
        else {
            val allTasks = taskRepository.all()
            for (task in allTasks) {
                taskRepository.update(Task(task.id, task.name, false))
            }
        }

        onChangedEnable()
        update(currentSelectType)
    }

    fun selectAll(view : View) {
        update(TaskSelectType.ALL)
    }

    fun selectActive(view : View) {
        update(TaskSelectType.ACTIVE)
    }

    fun selectCompleted(view : View) {
        update(TaskSelectType.COMPLETED)
    }

    override fun onCleared() {
        onChangedDisable()
        super.onCleared()
    }

    private fun onChangedEnable() {
        taskRepository.addOnChangedCallback(changedCallback)
    }

    private fun onChangedDisable() {
        taskRepository.removeOnChangedCallback(changedCallback)
    }

    private fun update(type : TaskSelectType) {
        currentSelectType = type

        val vms = mutableListOf<TaskViewModel>()
        val tasks = when(currentSelectType) {
            TaskSelectType.ALL -> taskRepository.all()
            TaskSelectType.ACTIVE -> taskRepository.active()
            TaskSelectType.COMPLETED -> taskRepository.completed()
        }

        for (task in tasks) {
            vms.add(TaskViewModelFactory(application, navigator, taskRepository, task).create(TaskViewModel::class.java))
        }

        mutableTaskViewModels.postValue(vms)
        leftCount.postValue("${taskRepository.active().count()} left item")
    }
}