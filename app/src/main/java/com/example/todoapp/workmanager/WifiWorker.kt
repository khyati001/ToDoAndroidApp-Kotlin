package com.example.todoapp.workmanager

import android.annotation.TargetApi
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.todoapp.R

class WifiWorker(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun doWork(): Result {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager.registerDefaultNetworkCallback(connectivityMangerCallback())
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                lollipopNetworkRequest()
            }
        }
        lollipopNetworkRequest()
        return Result.success()
    }


    /**
     * Method to create network request for lollipop version
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun lollipopNetworkRequest() {
        val requestBuilder =
            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        connectivityManager.registerNetworkCallback(
            requestBuilder.build(),
            connectivityMangerCallback()
        )
    }

    /**
     * Method to create network callback
     */
    private fun connectivityMangerCallback(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network) {
                    super.onLost(network)
                    displayNotification(context.getString(R.string.application_in_offline_mode_message))
                }

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    displayNotification(context.getString(R.string.application_has_internet_message))
                }
            }
            return networkCallback
        } else {
            throw IllegalAccessError("Error")
        }
    }

    /**
     * Method to display native notification
     */
    private fun displayNotification(value: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, "first").apply {
            setContentTitle(context.getString(R.string.new_notification))
            setContentText(value)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }.build()

        notificationManager.notify(1, notification)
    }
}