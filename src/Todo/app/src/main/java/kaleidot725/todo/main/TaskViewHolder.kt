package kaleidot725.todo.main

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kaleidot725.todo.databinding.TaskItemBinding
import kaleidot725.todo.model.entity.Task

class TaskViewHolder(owner : LifecycleOwner, itemView: View, binding : TaskItemBinding) : RecyclerView.ViewHolder(itemView) {
    private val binding : TaskItemBinding = binding
    private val owner = owner

    fun bind (vm : TaskViewModel?) {
        binding.vm = vm
        binding.executePendingBindings()
        binding.lifecycleOwner = owner
    }
}
