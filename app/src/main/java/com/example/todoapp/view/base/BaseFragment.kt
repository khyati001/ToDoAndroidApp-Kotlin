package com.example.todoapp.view.base

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    private lateinit var mAddFragmentHandler: AddFragmentHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        mAddFragmentHandler = AddFragmentHandler(activity?.supportFragmentManager)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        activity!!.title = getTitle()
    }

    abstract fun getTitle(): String?

    /**
     * Method to add fragment
     *
     * @param fragment fragment
     */
    protected open fun addFragment(fragment: BaseFragment) {
        mAddFragmentHandler.addFragment(fragment)
    }
}