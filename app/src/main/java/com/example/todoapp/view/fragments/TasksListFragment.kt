package com.example.todoapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.adapter.TasksListAdapter
import com.example.todoapp.databinding.FragmentTasksListBinding
import com.example.todoapp.util.Constants
import com.example.todoapp.view.base.BaseActivity
import com.example.todoapp.view.base.BaseFragment
import com.example.todoapp.viewmodel.UserTaskViewModel

class TasksListFragment : BaseFragment() {

    private lateinit var mFragmentTasksListBinding: FragmentTasksListBinding
    private lateinit var mUserTaskViewModel: UserTaskViewModel
    private lateinit var mTaskListAdapter: TasksListAdapter

    companion object {
        fun newInstance(): TasksListFragment {
            return TasksListFragment()
        }
    }

    override fun getTitle(): String {
        return "TasksListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentTasksListBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_tasks_list,
                container,
                false
            )
        initialization()
        return mFragmentTasksListBinding.root
    }

    /**
     * Method to initialize UI views
     */
    private fun initialization() {
        mUserTaskViewModel = ViewModelProvider(this).get(UserTaskViewModel::class.java)
        mFragmentTasksListBinding.fragmentTasksListRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            mFragmentTasksListBinding.fragmentTasksListRecyclerView.addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
        (activity as BaseActivity).setToolBarTitle(
            getString(R.string.header_todo_list), true
        )
        mFragmentTasksListBinding.fragmentTasksListFloatingActionButton.setOnClickListener {
            (activity as BaseActivity).addFragment(
                CreateTaskFragment.newInstance(Constants.ZERO, false)
            )
        }
        getUserTasksList()
    }

    /**
     * Method to observe user task's list and display it
     */
    private fun getUserTasksList() {
        mUserTaskViewModel.getUserTasksList(context!!)
            .observe(viewLifecycleOwner, {
                if (it.isEmpty()) {
                    mFragmentTasksListBinding.fragmentTasksListTextNoData.visibility = View.VISIBLE
                    mFragmentTasksListBinding.fragmentTasksListRecyclerView.visibility = View.GONE
                } else {
                    mFragmentTasksListBinding.fragmentTasksListTextNoData.visibility = View.GONE
                    mFragmentTasksListBinding.fragmentTasksListRecyclerView.visibility =
                        View.VISIBLE
                    mTaskListAdapter = TasksListAdapter(it, activity, mUserTaskViewModel)
                    mFragmentTasksListBinding.fragmentTasksListRecyclerView.adapter =
                        mTaskListAdapter
                }
            })
    }
}