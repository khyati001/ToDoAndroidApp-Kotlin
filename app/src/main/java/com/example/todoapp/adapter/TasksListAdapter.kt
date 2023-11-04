package com.example.todoapp.adapter

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TasksListItemBinding
import com.example.todoapp.model.UserTaskModel
import com.example.todoapp.view.base.BaseActivity
import com.example.todoapp.view.fragments.CreateTaskFragment
import com.example.todoapp.viewmodel.UserTaskViewModel

class TasksListAdapter(
    private val userTaskList: List<UserTaskModel>,
    private val activity: Activity?,
    private val mUserTaskViewModel: UserTaskViewModel
) : RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val taskListBinding: TasksListItemBinding) :
        RecyclerView.ViewHolder(taskListBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: TasksListItemBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.tasks_list_item,
            parent, false
        )
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int = userTaskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val userTask = userTaskList[position]
        holder.taskListBinding.taskList = userTask
        holder.taskListBinding.taskListItemBtnEdit.setOnClickListener {
            (activity as BaseActivity).addFragment(
                CreateTaskFragment.newInstance(userTaskList[position].id, true)
            )
        }
        holder.taskListBinding.taskListItemBtnDelete.setOnClickListener {
            AlertDialog.Builder(activity)
                .setMessage(activity!!.getString(R.string.alert_delete))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.dialog_yes)) { dialog, _ ->
                    mUserTaskViewModel.deleteUserTaskById(activity, userTaskList[position].id)
                    notifyItemChanged(position)
                    Toast.makeText(
                        activity,
                        activity.getString(R.string.task_delete_success_message),
                        Toast.LENGTH_LONG
                    ).show()
                    dialog.dismiss()
                }.setNegativeButton(
                    activity.getString(R.string.dialog_no)
                ) { dialog, _ -> dialog.dismiss() }.create().show()
        }
    }
}