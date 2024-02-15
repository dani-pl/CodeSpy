package com.danipl.codespy.domain

import com.danipl.codespy.data.Framework
import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.domain.models.AppInfo
import com.danipl.codespy.domain.models.toDomain
import javax.inject.Inject

/**
 * This use case fetches the apps that use Cordova in the user's phone.
 */
class GetCordovaAppsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    fun getCordovaApps(): List<AppInfo> {
        return packageManagerRepository.getAppsByFramework(Framework.CORDOVA).map {
            it.toDomain()
        }
    }
}