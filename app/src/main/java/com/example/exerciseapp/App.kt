package com.example.exerciseapp

import android.app.Application
import com.example.exerciseapp.di.appModule
import com.example.exerciseapp.di.localModule
import com.example.exerciseapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                networkModule,
                localModule,
                appModule,
            )
        }
    }
}
