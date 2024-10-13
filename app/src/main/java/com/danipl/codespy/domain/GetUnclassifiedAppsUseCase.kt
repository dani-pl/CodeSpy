package com.danipl.codespy.domain

import com.danipl.codespy.data.Framework
import com.danipl.codespy.data.PackageManagerRepository
import javax.inject.Inject

/**
 * This use case fetches the apps that use react native in the user's phone.
 */
class GetUnclassifiedAppsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    fun getUnclassifiedApps() = packageManagerRepository.getAppsByFramework(Framework.UNCLASSIFIED)
}