package com.danipl.codespy.data

import android.content.Context
import com.danipl.codespy.data.models.AppInfoDataModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PackageManagerRepository @Inject constructor(
    @ApplicationContext private val appContext: Context
) {


    private val pm = appContext.packageManager

    private val classifiedApps = mutableMapOf(
        Framework.REACT_NATIVE to mutableListOf<AppInfoDataModel>(),
        Framework.CORDOVA to mutableListOf<AppInfoDataModel>(),
        Framework.FLUTTER to mutableListOf<AppInfoDataModel>(),
        Framework.UNCLASSIFIED to mutableListOf<AppInfoDataModel>()
    )

    init {
        classifyApps()
    }

    private fun classifyApps() {
        pm.getInstalledApplications(0).forEach {
            var appFramework = Framework.UNCLASSIFIED
            when {
                isReactNativeApp(it.packageName) -> appFramework = Framework.REACT_NATIVE
                isCordovaApp(it.packageName) -> appFramework = Framework.CORDOVA
                isFlutterApp(it.packageName) -> appFramework = Framework.FLUTTER
            }
            classifiedApps[appFramework]?.add(
                AppInfoDataModel(
                    name = it.loadLabel(pm).toString(),
                    icon = it.loadIcon(pm),
                    packageName = it.packageName
                )
            )
        }
    }

    fun getAppsByFramework(framework: Framework): List<AppInfoDataModel>{
        return classifiedApps[framework]?.toList() ?: listOf()
    }

    private fun isReactNativeApp(packageName: String): Boolean {
        return pm
                .getResourcesForApplication(packageName)
                .assets
                .list("")
                ?.contains("index.android.bundle") ?: false
    }

    private fun isCordovaApp(packageName: String): Boolean {
        return pm
                .getResourcesForApplication(packageName)
                .assets
                .list("www")
                ?.contains("cordova.js") ?: false
    }

    private fun isFlutterApp(packageName: String): Boolean {
        return pm
            .getResourcesForApplication(packageName)
            .assets
            .list("flutter_assets")
            ?.contains("AssetManifest.json") ?: false
    }

}

enum class Framework {
    REACT_NATIVE, CORDOVA, FLUTTER, UNCLASSIFIED
}