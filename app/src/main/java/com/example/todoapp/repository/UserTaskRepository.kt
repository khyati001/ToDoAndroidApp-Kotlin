package com.example.todoapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todoapp.database.ToDoAppDatabase
import com.example.todoapp.database.dao.UserTaskDao
import com.example.todoapp.model.UserTaskModel
import com.example.todoapp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class UserTaskRepository(context: Context) {

    companion object {
        private var instance: UserTaskRepository? = null
        fun getInstance(context: Context): UserTaskRepository {
            if (instance == null)
                instance = UserTaskRepository(context)
            return instance!!
        }
    }

    private val userTaskDao: UserTaskDao = ToDoAppDatabase.getInstance(context).userTaskDao()

    /**
     * Method to insert or update user task details into database
     *
     * @param userTaskModel userTaskModel
     */
    fun executeUserTaskLocallyUpdate(userTaskModel: UserTaskModel) {
        if (userTaskModel.id == Constants.ZERO) {
            userTaskModel.createdOn = getCurrentTime()
            userTaskModel.updatedOn = getCurrentTime()
            insertUserTaskDetails(userTaskModel)
        } else {
            userTaskModel.updatedOn = getCurrentTime()
            updateUserTaskDetails(userTaskModel)
        }
    }

    /**
     * Method to insert new task detail into database
     *
     * @param userTaskModel userTaskModel
     */
    private fun insertUserTaskDetails(userTaskModel: UserTaskModel) {
        CoroutineScope(IO).launch {
            userTaskDao.insertUserTask(userTaskModel)
        }
    }

    /**
     * Method to update existing user task detail into database
     *
     * @param userTaskModel userTaskModel
     */
    private fun updateUserTaskDetails(userTaskModel: UserTaskModel) {
        CoroutineScope(IO).launch {
            userTaskDao.updateUserTask(
                userTaskModel.taskName, userTaskModel.taskDate, userTaskModel.taskCreator,
                userTaskModel.updatedOn, userTaskModel.id
            )
        }
    }

    /**
     * Method to get users tasks list live data
     */
    fun getUserTasksList(): LiveData<List<UserTaskModel>> {
        return userTaskDao.getUserTasks()
    }

    /**
     * Method to get task details by task id from database
     *
     * @param taskId taskId
     * @return Task details
     */
    fun getUserTaskById(taskId: Int): LiveData<UserTaskModel> {
        val mutableLiveData: MutableLiveData<UserTaskModel> =
            MutableLiveData<UserTaskModel>()
        CoroutineScope(IO).launch {
            val userTaskModel: UserTaskModel =
                userTaskDao.getUserTaskById(taskId)
            mutableLiveData.postValue(userTaskModel)
        }
        return mutableLiveData
    }

    /**
     * Method to delete task details from database
     *
     * @param taskId taskId
     */
    fun deleteUserTaskById(taskId: Int) {
        CoroutineScope(IO).launch {
            userTaskDao.deleteTaskById(taskId)
        }
    }

    /**
     * Method to get current time
     *
     * @return current time
     */
    private fun getCurrentTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(Date())
    }
}