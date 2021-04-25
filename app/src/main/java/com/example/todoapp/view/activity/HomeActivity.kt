package com.example.todoapp.view.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todoapp.R
import com.example.todoapp.view.base.BaseActivity
import com.example.todoapp.view.fragments.TasksListFragment
import com.example.todoapp.workmanager.WifiWorker
import java.util.concurrent.TimeUnit

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_container, TasksListFragment.newInstance(), null)
            .commit()
        createNotificationChannel()
        scheduleConnectivityCheckTask()
    }

    override fun getContentPageLayoutId(): Int = R.layout.activity_home

    /**
     * Method to schedule background task using work manager
     */
    private fun scheduleConnectivityCheckTask() {
        // build an object of PeriodicWorkRequestBuilder
        val workerRequest = PeriodicWorkRequest
            .Builder(
                WifiWorker::class.java, 1,
                TimeUnit.MINUTES
            ).build()
        //Enqueue the work request to an instance of Work Manager
        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    /**
     * Method to crrate notification channel
     */
    private fun createNotificationChannel() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    "first", "default",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }
    }
}
