package com.example.todoapp.view.base

import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.todoapp.R
import com.example.todoapp.util.Constants

class AddFragmentHandler(private val fragmentManager: FragmentManager?) {

    /**
     * Method to add fragment and maintain fragment back stack
     *
     * @param fragment fragment
     */
    fun addFragment(fragment: BaseFragment) {
        //don't add a fragment of the same type on top of itself.
        val currentFragment: BaseFragment? = getCurrentFragment()
        if (currentFragment?.javaClass === fragment.javaClass) {
            Log.w(
                "Fragment Manager",
                "Tried to add a fragment of the same type to the back stack. " +
                        "This may be done on purpose in some circumstances but generally should be avoided."
            )
            return
        }
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(
            R.id.fragment_container,
            fragment,
            fragment.getTitle()
        )
        fragmentTransaction.addToBackStack(fragment.getTitle())
        fragmentTransaction.commit()
    }

    /**
     * Method to get current fragment
     */
    private fun getCurrentFragment(): BaseFragment? {
        if (fragmentManager!!.backStackEntryCount == Constants.ZERO) {
            return null
        }
        val currentEntry = fragmentManager.getBackStackEntryAt(
            fragmentManager.backStackEntryCount - Constants.ONE
        )
        val tag = currentEntry.name
        val fragment = fragmentManager.findFragmentByTag(tag)
        return fragment as BaseFragment
    }

}