package com.syxl.customviewdemo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import java.util.*
import kotlin.concurrent.timerTask

class MyService : Service() {
    private val TAG = "MyService"

    var timer = Timer()
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var timerTask = timerTask {
            Log.e(TAG,"time="+System.currentTimeMillis())
        }
        timer.schedule(timerTask,500,4000)
        return super.onStartCommand(intent, flags, startId)
    }
}
