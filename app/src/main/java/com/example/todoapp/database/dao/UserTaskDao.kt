package com.example.todoapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.model.UserTaskModel

@Dao
interface UserTaskDao {

    @Insert
    fun insertUserTask(userTaskModel: UserTaskModel)

    @Query("update UserTask set taskName = :taskName, taskDate = :taskDate, taskCreator = :taskCreator, updatedOn = :updatedOn where id = :taskId")
    fun updateUserTask(
        taskName: String,
        taskDate: String,
        taskCreator: String,
        updatedOn: String,
        taskId: Int
    )

    @Query("select * from UserTask")
    fun getUserTasks(): LiveData<List<UserTaskModel>>

    @Query("select * from UserTask where id = :taskId")
    fun getUserTaskById(taskId: Int): UserTaskModel

    @Query("delete from UserTask where id = :taskId")
    fun deleteTaskById(taskId: Int)
}