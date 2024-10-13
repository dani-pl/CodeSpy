package com.danipl.codespy.domain

import com.danipl.codespy.data.PackageManagerRepository
import javax.inject.Inject

/**
 * This use case classifies and stores the app in the user's phone by the framework used to build them.
 */
class ClassifyAndStoreUserAppsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    operator fun invoke() = packageManagerRepository
        .classifyAndStoreApps()
}