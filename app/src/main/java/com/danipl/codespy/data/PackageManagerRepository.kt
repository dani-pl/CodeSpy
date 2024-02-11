package com.danipl.codespy.data

import android.content.Context
import com.danipl.codespy.data.models.AppInfoDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class PackageManagerRepository @Inject constructor(
    @ApplicationContext private val appContext: Context
) {

    private val pm = appContext.packageManager

    fun getReactNativeApps(): List<AppInfoDataModel> {
        val installedReactNativeApps = mutableListOf<AppInfoDataModel>()
        pm.getInstalledApplications(0).forEach {
            if (isReactNativeApp(it.packageName)) {
                installedReactNativeApps.add(
                    AppInfoDataModel(
                        name = it.loadLabel(pm).toString(),
                        icon = it.loadIcon(pm),
                        packageName = it.packageName
                    )
                )
            } else if (isCordovaApp(it.packageName)){
                Timber.d("The app ${it.packageName} is a CordovaApp")

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

    private fun isCordovaApp(packageName: String): Boolean {
        return pm
                .getResourcesForApplication(packageName)
                .assets
                .list("www")
                ?.contains("cordova.js") ?: false
    }

}

enum class Framework {
    REACT_NATIVE, CORDOVA, UNCLASSIFIED
}