package com.example.todoapp.view.base

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.example.todoapp.R
import com.example.todoapp.util.Constants
import com.example.todoapp.util.Utils

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mToolBarBack: ImageView
    private lateinit var mToolBarTitle: TextView
    private lateinit var mPageContentHolder: FrameLayout
    private lateinit var mAddFragmentHandler: AddFragmentHandler
    private lateinit var mFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        mFragmentManager = supportFragmentManager
        mAddFragmentHandler = AddFragmentHandler(mFragmentManager)
        setPageView()
        initListeners()
    }

    /**
     * Method to set base layout views with toolbar
     */
    private fun setPageView() {
        val mPageContent = findViewById<LinearLayout>(R.id.page_content)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        mToolBarBack = toolbar.findViewById(R.id.toolbar_back)
        mToolBarTitle = toolbar.findViewById(R.id.toolbar_title)
        mPageContentHolder = findViewById(R.id.page_content_holder)
        val childView: View =
            layoutInflater.inflate(getContentPageLayoutId(), mPageContent, false)
        toolbar.filterTouchesWhenObscured = true
        mPageContentHolder.filterTouchesWhenObscured = true
        mPageContent.addView(childView, Constants.ZERO)
    }

    /**
     * To get the content of the page to be displayed
     *
     * @return layout's id.
     */
    @LayoutRes
    protected abstract fun getContentPageLayoutId(): Int

    /**
     * Method to set views listeners
     */
    private fun initListeners() {
        mToolBarBack.setOnClickListener {
            onBackPressed()
        }
    }

    /**
     * Method to show tool bar title
     *
     * @param value value
     * @param hideBack hideBack
     */
    open fun setToolBarTitle(value: String?, hideBack: Boolean) {
        if (hideBack) {
            mToolBarBack.visibility = View.INVISIBLE
        } else {
            showToolBarBackBtn()
        }
        mToolBarTitle.visibility = View.VISIBLE
        mToolBarTitle.text = value
    }

    /**
     * Show tool bar back button
     */
    open fun showToolBarBackBtn() {
        mToolBarBack.visibility = View.VISIBLE
    }

    /**
     * Hide tool bar back button
     */
    open fun hideToolbarBackBtn() {
        mToolBarBack.visibility = View.GONE
    }

    /**
     * Method to add fragment
     *
     * @param fragment fragment
     */
    open fun addFragment(fragment: BaseFragment) {
        mAddFragmentHandler.addFragment(fragment)
    }

    override fun onBackPressed() {
        Utils.hideSoftKeyboard(this)
        val backStackCount = supportFragmentManager.backStackEntryCount
        if (backStackCount > Constants.ZERO) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun finish() {
        super.finish()
        Utils.hideSoftKeyboard(this)
    }
}