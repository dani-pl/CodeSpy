package com.danipl.codespy.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.graphics.drawable.toBitmap
import com.danipl.codespy.data.db.AppDatabase
import com.danipl.codespy.data.db.UserAppEntity
import com.danipl.codespy.util.Framework
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PackageManagerRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val appDatabase: AppDatabase
) {


    private val pm = appContext.packageManager

    private val classifiedApps = mapOf(
        Framework.REACT_NATIVE to mutableListOf<UserAppEntity>(),
        Framework.FLUTTER to mutableListOf(),
        Framework.CORDOVA to mutableListOf()
    )

    fun classifyAndStoreApps(): PackageManagerResult  =
        try {
            classifyApps()
            storeAppsInDb()
            PackageManagerResult.Success
        } catch (e: Exception) {
            PackageManagerResult.Error
        }

    private fun classifyApps() {
        pm.getInstalledApplications(0).forEach { appInfo ->

            val appFramework = when {
                isReactNativeApp(appInfo.packageName) -> Framework.REACT_NATIVE
                isFlutterApp(appInfo.packageName) -> Framework.FLUTTER
                isCordovaApp(appInfo.packageName) -> Framework.CORDOVA
                else -> null
            }


            appFramework?.let {
                classifiedApps[appFramework]?.add(
                    UserAppEntity(
                        name = appInfo.loadLabel(pm).toString(),
                        iconUri = getAppIconUriFromDrawable(
                            appId = appInfo.packageName,
                            drawable = appInfo.loadIcon(pm)
                        ).toString(),
                        packageName = appInfo.packageName,
                        framework = it.toString()
                    )
                )
            }
        }
    }

    private fun storeAppsInDb() {
        appDatabase.userAppEntityDao().insertAll(*classifiedApps.flatMap { it.value}.toTypedArray())
    }

    private fun getAppIconUriFromDrawable(appId: String, drawable: Drawable): Uri {
        val file = File(appContext.filesDir, appId)
        val outputStream = FileOutputStream(file)
        drawable.toBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return Uri.fromFile(file) // Return the path to store in the database
    }


    fun getAppsByFramework(framework: Framework, forceReadFromDb: Boolean = false) =
        if(!classifiedApps[framework].isNullOrEmpty() && !forceReadFromDb){
            // Return cached list
            classifiedApps[framework] ?: listOf<UserAppEntity>()
        } else {
            // Read list from database
            appDatabase.userAppEntityDao().loadAllByFramework(framework.toString())
        }

    fun deleteAllApps(): PackageManagerResult =
        try {
            appDatabase.userAppEntityDao().deleteAll(*classifiedApps.flatMap {it.value}.toTypedArray())
            PackageManagerResult.Success
        } catch (e: Exception) {
            PackageManagerResult.Error
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

sealed class PackageManagerResult {

    data object Success: PackageManagerResult()


    data object Error: PackageManagerResult()
}