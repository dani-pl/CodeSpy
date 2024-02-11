package com.danipl.codespy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class CodeSpy : Application() {
    override fun onCreate() {
        super.onCreate()

        plant(Timber.DebugTree())
    }
}
