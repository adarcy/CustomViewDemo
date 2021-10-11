package com.syxl.customviewdemo

import android.app.Application
import tech.linjiang.pandora.Pandora


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

//        BlockCanary.install(this, BlockCanaryContext()).start()
        Pandora.get().open()
    }
}