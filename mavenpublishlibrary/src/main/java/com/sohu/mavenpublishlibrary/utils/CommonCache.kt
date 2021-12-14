package com.okay.lib_common.utils

import android.content.Context
import com.tencent.mmkv.MMKV

/**
 * 替换sp进行本地数据存储
 */
object CommonCache {
    private lateinit var mmkv: MMKV

    fun initCache(context: Context, key: String): MMKV {
        MMKV.initialize(context)
//        mmkv = MMKV.defaultMMKV()
        //根据workId 来存储
        mmkv = MMKV.mmkvWithID(key, MMKV.MULTI_PROCESS_MODE)
        return mmkv
    }

    /**
     * 默认id
     */
    fun getCacheById(id: String): MMKV {
        return MMKV.mmkvWithID(id)
    }


    fun setCache(key: String, value: String) {
        mmkv.encode(key, value)
    }

    fun setCache(key: String, value: Int) {
        mmkv.encode(key, value)
    }

    fun setCache(key: String, value: Long) {
        mmkv.encode(key, value)
    }

    fun setCache(key: String, value: Float) {
        mmkv.encode(key, value)
    }

    fun setCache(key: String, value: Double) {
        mmkv.encode(key, value)
    }

    fun setCache(key: String, value: Boolean) {
        mmkv.encode(key, value)
    }

    fun getBoolValue(key: String): Boolean {
        return mmkv.decodeBool(key)
    }

    fun getIntValue(key: String): Int {
        return mmkv.decodeInt(key)
    }

    fun getStringValue(key: String): String {
        return mmkv.decodeString(key)
    }

    fun getFloatValue(key: String): Float {
        return mmkv.decodeFloat(key)
    }

    fun getDoubleValue(key: String): Double {
        return mmkv.decodeDouble(key)
    }

    fun getLongValue(key: String): Long {
        return mmkv.decodeLong(key)
    }

    fun exitCache() {
        MMKV.onExit()
    }

    fun clearCache() {
        mmkv.clearAll()
    }
}
