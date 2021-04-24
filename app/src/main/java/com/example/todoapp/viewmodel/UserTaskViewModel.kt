package com.example.todoapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.model.UserTaskModel
import com.example.todoapp.repository.UserTaskRepository

class UserTaskViewModel : ViewModel() {

    /**
     * Method to call repository function to insert or update user task  details into database
     *
     * @param userTaskModel userTaskModel
     */
    fun executeUserTaskLocallyUpdate(context: Context, userTaskModel: UserTaskModel) {
        UserTaskRepository.getInstance(context).executeUserTaskLocallyUpdate(userTaskModel)
    }

    /**
     * Method to call repository function to get users tasks list live data
     */
    fun getUserTasksList(context: Context): LiveData<List<UserTaskModel>> =
        UserTaskRepository.getInstance(context).getUserTasksList()

    /**
     * Method to call repository function to get task details from database
     *
     * @param taskId taskId
     * @return Task details
     */
    fun getUserTaskById(context: Context, taskId: Int): LiveData<UserTaskModel> =
        UserTaskRepository.getInstance(context).getUserTaskById(taskId)

    /**
     * Method to call repository function to delete task details from database
     *
     * @param taskId taskId
     * @return Task details
     */
    fun deleteUserTaskById(context: Context, taskId: Int) {
        UserTaskRepository.getInstance(context).deleteUserTaskById(taskId)
    }
}