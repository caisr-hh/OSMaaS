package com.example.varbergpoi

import android.content.Context
import com.example.varbergpoi.dummydata.MyObjectBox
import io.objectbox.BoxStore

class ObjectBox {
    companion object {
        lateinit var boxStore: BoxStore
            private set

        /**
         * Initializes the objectbox instance
         * @property context Context should always be supplied on android, it is nullable only for testing
         */
        fun init(context: Context?) {
            boxStore = if(context != null)
                MyObjectBox.builder().androidContext(context.applicationContext).build()
            else
                MyObjectBox.builder().build()
        }
    }
}