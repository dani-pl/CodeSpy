package com.danipl.codespy.domain

import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.util.Framework
import javax.inject.Inject

/**
 * This use case fetches the apps that use react native in the user's phone.
 */
class GetReactNativeAppsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    operator fun invoke() = packageManagerRepository.getAppsByFramework(Framework.REACT_NATIVE)
}