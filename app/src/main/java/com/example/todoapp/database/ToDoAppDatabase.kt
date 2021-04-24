package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.database.dao.UserTaskDao
import com.example.todoapp.model.UserTaskModel

@Database(entities = [(UserTaskModel::class)], version = 1)
abstract class ToDoAppDatabase : RoomDatabase() {

    companion object {
        // marking the instance as volatile to ensure atomic access to the variable
        @Volatile
        private var instance: ToDoAppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ToDoAppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext, ToDoAppDatabase::class.java,
                    "todo_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!
        }
    }

    abstract fun userTaskDao(): UserTaskDao
}