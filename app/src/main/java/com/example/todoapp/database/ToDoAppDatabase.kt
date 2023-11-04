package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.database.dao.UserTaskDao
import com.example.todoapp.model.UserTaskModel

@Database(entities = [UserTaskModel::class], version = 1)
abstract class ToDoAppDatabase : RoomDatabase() {

    companion object {
        // marking the instance as volatile to ensure atomic access to the variable
        @Volatile
        private var INSTANCE: ToDoAppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ToDoAppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, ToDoAppDatabase::class.java,
                    "todo_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                // Return instance
                instance
            }
        }
    }

    abstract fun userTaskDao(): UserTaskDao
}