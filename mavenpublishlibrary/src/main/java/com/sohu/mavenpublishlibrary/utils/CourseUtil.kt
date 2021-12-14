package com.okay.lib_common.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 *@author wzj
 *@date 2020/12/7 2:16 PM
 * 处理进入课堂逻辑
 */
object CourseUtil {
    private const val TAG = "CourseUtil"
    private var liveCount = 0

    /**
     * 智课场景后续有智课场景要加入
     */
    private val courseScene = listOf(
        "com.okay.classroom.view.activity.LockActivity",
        "com.okay.word.activity.LearnActivity",
        "com.okay.word.activity.MainActivity",
        "com.okay.word.activity.VideoPlayerActivity",
        "com.okay.word.activity.WhiteBoardActivity",
        "com.okay.word.activity.IMActivity"
    )

    /**
     * 是否是上课场景(智课场景不能唤起小O)
     * @param activity
     * @return
     */
    fun inLiveSence(activity: Activity): Boolean {
        return courseScene.contains(activity.localClassName)
    }

    fun exitMainActivity(activity: Activity): Boolean {
        return "com.okay.word.activity.MainActivity" == activity.localClassName
    }

    fun onStart(context: Context) {
        liveCount++
        sendBroadCast(context)
        Log.d(TAG,"onStart liveCount = $liveCount")
    }

    fun onStop(context: Context) {
        liveCount--
        sendBroadCast(context)
        Log.d(TAG,"onStop liveCount = $liveCount")
    }

    /**
     * 只在MainActivity添加
     */
    fun onDestory(context: Context) {
        liveCount = 0
        sendBroadCast(context)
        Log.d(TAG,"onDestory liveCount = $liveCount")
    }

    private fun sendBroadCast(context: Context) {
        var intent = Intent("com.okay.microlecture.change.course.status")
        intent.putExtra("inCourse", liveCount > 0)
        context.sendBroadcast(intent)
    }
}