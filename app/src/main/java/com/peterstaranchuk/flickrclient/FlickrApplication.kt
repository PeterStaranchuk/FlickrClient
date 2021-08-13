package com.peterstaranchuk.flickrclient

import android.app.Application
import com.peterstaranchuk.flickrclient.di.redirectorsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlickrApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FlickrApplication)
            modules(redirectorsModule)
        }
    }
}