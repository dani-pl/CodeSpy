package com.danipl.frameworkguesser.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdbRepository @Inject constructor(
    @ApplicationContext private val appContext: Context
) {

    private val pm = appContext.packageManager

    fun getReactNativeApps(): List<String> {
        val installedReactNativeApps = mutableListOf<String>()
        pm.getInstalledApplications(0).forEach {
            if (isReactNativeApp(it.packageName)){
                installedReactNativeApps.add(it.loadLabel(pm).toString())
            }
        }
        return installedReactNativeApps
    }

    private fun isReactNativeApp(packageName: String): Boolean {
        return try {
            pm
                .getResourcesForApplication(packageName)
                .assets
                .open("index.android.bundle")
            true
        } catch (e: Exception) {
            false
        }
    }
}