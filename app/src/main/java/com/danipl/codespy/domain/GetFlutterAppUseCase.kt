package com.danipl.codespy.domain

import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.domain.models.toUserAppList
import com.danipl.codespy.util.Framework
import javax.inject.Inject

/**
 * This use case fetches the apps that use Flutter in the user's phone.
 */
class GetFlutterAppsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    operator fun invoke() = packageManagerRepository
        .getAppsByFramework(Framework.FLUTTER)
        .toUserAppList()
}