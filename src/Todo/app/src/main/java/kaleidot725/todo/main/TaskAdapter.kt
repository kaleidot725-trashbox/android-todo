package kaleidot725.todo.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableList
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kaleidot725.todo.R
import kaleidot725.todo.databinding.TaskItemBinding
import kaleidot725.todo.model.entity.Task

class TaskAdapter(owner : LifecycleOwner, vms: List<TaskViewModel>) : RecyclerView.Adapter<TaskViewHolder>() {
    private val owner : LifecycleOwner = owner
    private val vms: MutableList<TaskViewModel> = vms.toMutableList()

    fun update(new : List<TaskViewModel>) {
        vms.clear()
        vms.addAll(new)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val binding = DataBindingUtil.inflate<TaskItemBinding>(inflater, R.layout.task_item, parent, false)
        return TaskViewHolder(owner, binding.root, binding)
    }

    override fun onBindViewHolder(holderTask: TaskViewHolder, position: Int) {
        holderTask.bind(vms[position])
    }

    override fun getItemCount(): Int = vms?.count() ?: 0
}