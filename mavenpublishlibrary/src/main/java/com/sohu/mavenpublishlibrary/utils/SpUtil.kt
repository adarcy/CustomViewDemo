package com.okay.lib_common.utils

import android.content.Context

object SpUtil {

    private val SP_NAME = "config"
    /**
     * 存储string
     *
     * @param mContext
     * @param key
     * @param values
     */
    fun putString(mContext: Context, key: String, values: String) {
        val sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, values)
        editor.apply()
    }

    /**
     * 读取string
     *
     * @param mContext
     * @param key
     * @param defValues
     * @return
     */
    fun getString(mContext: Context, key: String, defValues: String): String? {
        val sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getString(key, defValues)
    }

    /**
     * 存储int
     *
     * @param mContext
     * @param key
     * @param values
     */
    fun putInt(mContext: Context, key: String, values: Int) {
        val sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putInt(key, values)
        editor.apply()
    }

    /**
     * 读取int
     *
     * @param mContext
     * @param key
     * @param defValues
     * @return
     */
    fun getInt(mContext: Context, key: String, defValues: Int): Int {
        val sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(key, defValues)
    }

    /**
     * 存储boolean
     *
     * @param mContext
     * @param key
     * @param values
     */
    fun putBoolean(mContext: Context, key: String, values: Boolean) {
        val sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(key, values)
        editor.apply()
    }

    /**
     * 读取boolean
     *
     * @param mContext
     * @param key
     * @param defValues
     * @return
     */
    fun getBoolean(mContext: Context, key: String, defValues: Boolean): Boolean {
        val sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(key, defValues)
    }

    /**
     * 删除一条数据
     *
     * @param mContext
     * @param key
     */
    fun deleKey(mContext: Context, key: String) {
        val sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.remove(key)
        editor.apply()
    }

    /**
     * 删除整个数据
     *
     * @param mContext
     * @param key
     */
    fun deleAll(mContext: Context, key: String) {
        val sp = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }
}