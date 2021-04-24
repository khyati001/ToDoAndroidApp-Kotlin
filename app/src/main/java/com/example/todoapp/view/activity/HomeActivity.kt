package com.example.todoapp.view.activity

import android.os.Bundle
import com.example.todoapp.R
import com.example.todoapp.view.base.BaseActivity
import com.example.todoapp.view.fragments.TasksListFragment

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container, TasksListFragment.newInstance(), null)
            .commit()
    }

    override fun getContentPageLayoutId(): Int = R.layout.activity_home
}