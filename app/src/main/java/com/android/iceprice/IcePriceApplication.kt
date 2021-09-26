package com.android.iceprice

import android.app.Application
import com.chibatching.kotpref.Kotpref
import timber.log.Timber
import timber.log.Timber.DebugTree


class IcePriceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this@IcePriceApplication)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

}