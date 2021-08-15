package com.peterstaranchuk.flickrclient

import android.app.Application
import com.peterstaranchuk.common.networkModule
import com.peterstaranchuk.flickrclient.di.redirectorsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class FlickrApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FlickrApplication)
            modules(listOf(redirectorsModule, networkModule))
        }

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}