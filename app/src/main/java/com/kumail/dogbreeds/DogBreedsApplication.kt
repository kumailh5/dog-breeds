package com.kumail.dogbreeds

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by kumailhussain on 12/10/2021.
 */
@HiltAndroidApp
class DogBreedsApplication : Application() {

    companion object {
        lateinit var instance: DogBreedsApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        instance = this
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}