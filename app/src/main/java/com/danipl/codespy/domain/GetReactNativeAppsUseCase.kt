package com.danipl.codespy.domain

import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.domain.models.AppInfo
import com.danipl.codespy.domain.models.toDomain
import javax.inject.Inject

/**
 * This use case fetches the apps that use react native in the user's phone.
 */
class GetReactNativeAppsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    fun getReactNativeApps(): List<AppInfo> {
        return packageManagerRepository.getReactNativeApps().map {
            it.toDomain()
        }
    }
}