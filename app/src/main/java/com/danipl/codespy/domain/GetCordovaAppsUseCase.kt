package com.danipl.codespy.domain

import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.domain.models.toUserAppList
import com.danipl.codespy.util.Framework
import javax.inject.Inject

/**
 * This use case fetches the apps that use Cordova in the user's phone.
 */
class GetCordovaAppsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    operator fun invoke() = packageManagerRepository
        .getAppsByFramework(Framework.CORDOVA)
        .toUserAppList()
}