package kaleidot725.todo.main

import android.app.Person
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kaleidot725.todo.R
import kaleidot725.todo.databinding.TaskItemBinding
import kaleidot725.todo.model.Task

class TaskAdapter(list: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private val list: List<Task> = list

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById<CheckBox>(R.id.checked)
        val name: TextView = itemView.findViewById<TextView>(R.id.name)
        val delete: ImageButton = itemView.findViewById<ImageButton>(R.id.delete)

        fun bind (task : Task?) {
            checkBox.isChecked = if (task == null || !task?.checked) false else true
            name.text = task?.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false) as View
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.count()
}