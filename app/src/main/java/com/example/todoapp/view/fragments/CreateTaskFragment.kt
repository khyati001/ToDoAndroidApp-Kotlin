package com.example.todoapp.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCreateTaskBinding
import com.example.todoapp.model.UserTaskModel
import com.example.todoapp.util.Constants
import com.example.todoapp.util.Constants.Companion.THOUSAND
import com.example.todoapp.view.base.BaseActivity
import com.example.todoapp.view.base.BaseFragment
import com.example.todoapp.viewmodel.UserTaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CreateTaskFragment : BaseFragment(), View.OnClickListener {

    private lateinit var mCreateTaskBinding: FragmentCreateTaskBinding
    private lateinit var mDateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var mUserTaskViewModel: UserTaskViewModel
    private var mUserTasksList: List<UserTaskModel> = listOf()
    private var mCalendar: Calendar = Calendar.getInstance()
    private var mIsEditMode: Boolean = false
    private var mTaskId: Int = Constants.ZERO
    private var mTaskDate: String = ""
    private var mTaskCreator: String = ""

    companion object {
        fun newInstance(id: Int, isEditMode: Boolean): CreateTaskFragment {
            val fragment = CreateTaskFragment()
            val bundle = Bundle()
            bundle.putInt(Constants.BUNDLE_TASK_ID, id)
            bundle.putBoolean(Constants.BUNDLE_IS_EDITABLE, isEditMode)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getTitle(): String {
        return Constants.CREATE_TASK_FRAGMENT_TAG
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mCreateTaskBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_create_task,
                container,
                false
            )
        initialization()
        return mCreateTaskBinding.root
    }

    /**
     * Method to initialize UI views
     */
    private fun initialization() {
        getBundleData()
        mUserTaskViewModel = ViewModelProvider(this).get(UserTaskViewModel::class.java)
        (activity as BaseActivity).setToolBarTitle(
            getString(R.string.create_task), false
        )
        mDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                mCalendar.set(Calendar.YEAR, year)
                mCalendar.set(Calendar.MONTH, monthOfYear)
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        mCreateTaskBinding.fragmentCreateTaskEdtDate.setOnClickListener(this)
        mCreateTaskBinding.fragmentCreateTaskBtnSave.setOnClickListener(this)
        getUserTasksList()
        getTaskDetailsItem()
    }

    /**
     * Method to get data from bundle
     */
    private fun getBundleData() {
        val bundle = arguments
        if (bundle != null) {
            mIsEditMode = bundle.getBoolean(Constants.BUNDLE_IS_EDITABLE)
            mTaskId = bundle.getInt(Constants.BUNDLE_TASK_ID)
        }
    }

    override fun onClick(view: View?) {
        val id = view?.id
        if (id == R.id.fragment_create_task_edt_date) {
            openDatePickerDialog()
        } else if (id == R.id.fragment_create_task_btn_save) {
            validateFields()
        }
    }

    /**
     * Method to get task details item from local db and display it
     */
    private fun getTaskDetailsItem() {
        mUserTaskViewModel.getUserTaskById(requireContext(), mTaskId).observe(viewLifecycleOwner) {
            if (it != null)
                mCreateTaskBinding.taskItem = it
            else
                updateDateInView()
            mTaskDate = mCreateTaskBinding.fragmentCreateTaskEdtDate.text.toString()
            mTaskCreator = mCreateTaskBinding.fragmentCreateTaskEdtTaskCreator.text.toString()
        }
    }

    /**
     * Method to observe user task's list and display it
     */
    private fun getUserTasksList() {
        mUserTaskViewModel.getUserTasksList(requireContext())
            .observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    mUserTasksList = it
                }
            }
    }

    /**
     * Method to open and display native date picker dialog
     */
    private fun openDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            mDateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - THOUSAND
        datePickerDialog.show()
    }

    /**
     * Method to set and update date in edit text view
     */
    private fun updateDateInView() {
        val dateFormat = "MM/dd/yyyy"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
        mCreateTaskBinding.fragmentCreateTaskEdtDate.setText(simpleDateFormat.format(mCalendar.time))
    }

    /**
     * Method to check basic empty validation and proceed
     */
    private fun validateFields() {
        if (mCreateTaskBinding.fragmentCreateTaskEdtTaskName.text.isNullOrEmpty()) {
            mCreateTaskBinding.fragmentCreateTaskEdtTaskName.error =
                getString(R.string.validation_task_name_error_message)
            return
        }
        if (mCreateTaskBinding.fragmentCreateTaskEdtTaskCreator.text.isNullOrEmpty()) {
            mCreateTaskBinding.fragmentCreateTaskEdtTaskCreator.error =
                getString(R.string.validation_task_creator_error_message)
            return
        }
        updateUserTaskData()
    }

    /**
     * Method to insert/update user task details into local database
     */
    private fun updateUserTaskData() {
        val userTask = UserTaskModel()
        userTask.id = mTaskId
        userTask.taskName = mCreateTaskBinding.fragmentCreateTaskEdtTaskName.text.toString()
        userTask.taskDate = mCreateTaskBinding.fragmentCreateTaskEdtDate.text.toString()
        userTask.taskCreator = mCreateTaskBinding.fragmentCreateTaskEdtTaskCreator.text.toString()
        if (mUserTasksList.isNotEmpty()) {
            for (items in mUserTasksList) {
                if (items.taskName.equals(userTask.taskName, true)) {
                    Toast.makeText(
                        context,
                        getString(R.string.validation_same_task_message),
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }
            }
        }
        mUserTaskViewModel.executeUserTaskLocallyUpdate(requireContext(), userTask)
        if (mIsEditMode)
            Toast.makeText(
                context,
                getString(R.string.task_update_success_message),
                Toast.LENGTH_LONG
            ).show()
        else
            Toast.makeText(context, getString(R.string.task_add_success_message), Toast.LENGTH_LONG)
                .show()
        activity?.supportFragmentManager?.popBackStack()
    }
}