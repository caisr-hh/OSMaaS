package com.example.varbergpoi

import android.app.Application
import com.example.varbergpoi.dummydata.DummyHandler
import io.objectbox.android.BuildConfig
import timber.log.Timber

class POIApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
        DummyHandler.initDummyData()

        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}