package com.danipl.codespy.data

import android.content.Context
import com.danipl.codespy.data.models.AppInfoDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AdbRepository @Inject constructor(
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