package com.danipl.codespy.domain

import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.util.Framework
import javax.inject.Inject

/**
 * This use case fetches the apps in the user's phone whose frameworks could not been classified.
 */
class GetUnclassifiedAppsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    operator fun invoke() = packageManagerRepository.getAppsByFramework(Framework.UNCLASSIFIED)
}