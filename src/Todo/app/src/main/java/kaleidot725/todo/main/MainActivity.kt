package kaleidot725.todo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kaleidot725.todo.R
import kaleidot725.todo.model.entity.Task
import kaleidot725.todo.model.persistence.JsonPersistence
import kaleidot725.todo.model.repository.DefaultTaskRepository
import kaleidot725.todo.model.repository.TaskRepository
import android.app.AlertDialog
import android.widget.EditText
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import kaleidot725.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainNavigator {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var taskViewModels : MutableList<TaskViewModel>
    private lateinit var viewAdapter: TaskAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var repository: TaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar)

        repository = DefaultTaskRepository(JsonPersistence<Task>(application.filesDir.path + "task.json", Task::class.java))
        repository.init()

        mainViewModel = MainViewModelFactory(application, this, repository).create(MainViewModel::class.java)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.vm = mainViewModel

        viewManager = LinearLayoutManager(this)
        viewAdapter = TaskAdapter(this, mainViewModel.taskViewModels.value ?: arrayListOf<TaskViewModel>())
        mainViewModel.taskViewModels.observe(this, Observer{ viewAdapter.update(it) })
        this.findViewById<RecyclerView>(R.id.task_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
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
}
