package kaleidot725.todo.main

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kaleidot725.todo.R
import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.persistence.JsonPersistence
import kaleidot725.todo.model.repository.DefaultTaskRepository
import kaleidot725.todo.model.repository.TaskRepository
import java.lang.Exception
import android.app.AlertDialog
import android.widget.EditText
import android.widget.LinearLayout

class MainActivity : AppCompatActivity(), MainNavigator {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var taskViewModels : MutableList<TaskViewModel>
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var repository: TaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar)
        setContentView(R.layout.activity_main)

        repository = DefaultTaskRepository(JsonPersistence<Task>(application.filesDir.path + "task.json", Task::class.java))
        repository.init()

        mainViewModel = MainViewModelFactory(application, repository).create(MainViewModel::class.java)
        taskViewModels = mutableListOf()

        viewManager = LinearLayoutManager(this)
        viewAdapter = TaskAdapter(this, taskViewModels)
        this.findViewById<RecyclerView>(R.id.task_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // ViewModelの実態を管理するのはActivityとする。
        // なのでいらなくなったタイミングでコールバックで通知しRecyclerViewから消す
        for (task in repository.all()) {
            val vm = TaskViewModelFactory(application, this, repository, task).create(TaskViewModel::class.java).also {
                it.disposed = {
                    taskViewModels.remove(it)
                    viewAdapter.notifyDataSetChanged()
                }
            }
            taskViewModels.add(vm)
        }

        viewAdapter.notifyDataSetChanged()
    }

    override fun editTextDialog(title : String, current : String, apply: (name: String) -> Unit) {

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(30, 5, 5, 5)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)

        val view = layoutInflater.inflate(R.layout.input_dialog, null)
        builder.setView(view)

        val input = view.findViewById<EditText>(R.id.input_text)
        input.setText(current)

        builder.setPositiveButton("OK") { dialog, it -> apply.invoke(input.text.toString())}
        builder.setNegativeButton("Cancel") { dialog, it -> dialog.cancel() }
        builder.show()
    }

    private class MainViewModelFactory(
        application: Application,
        val repository : TaskRepository)
        : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == kaleidot725.todo.main.MainViewModel::class.java) {
                return (MainViewModel(repository) as T)
            }

            throw Exception("Invalid Model Class")
        }
    }

    private class TaskViewModelFactory(
        application: Application,
        val navigator : MainNavigator,
        val repository : TaskRepository,
        val task : Task)
        : ViewModelProvider.AndroidViewModelFactory(application) {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass == TaskViewModel::class.java) {
                return TaskViewModel(navigator, repository, task) as T
            }

            throw Exception("Invalid Model Class")
        }
    }
}
