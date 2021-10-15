package com.kumail.dogbreeds

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by kumailhussain on 12/10/2021.
 */
@HiltAndroidApp
class DogBreedsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}