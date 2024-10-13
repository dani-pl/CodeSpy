package com.danipl.codespy.domain

import com.danipl.codespy.data.Framework
import com.danipl.codespy.data.PackageManagerRepository
import javax.inject.Inject

/**
 * This use case fetches the apps that use Flutter in the user's phone.
 */
class GetFlutterAppsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    fun getFlutterApps() = packageManagerRepository.getAppsByFramework(Framework.FLUTTER)
}