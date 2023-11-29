package com.example.varbergpoi

import android.app.Application
import com.example.varbergpoi.dummydata.DummyHandler
import io.objectbox.android.BuildConfig
import timber.log.Timber

class POIApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Timber.tag("MyPOITag") // Set a custom tag
        Timber.d("POI app Data Entered")

        ObjectBox.init(this)
        DummyHandler.initDummyData(assets)




    }
}