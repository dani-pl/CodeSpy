package com.danipl.codespy.domain

import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.data.PackageManagerResult
import javax.inject.Inject

class RefreshAllAppListsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    operator fun invoke(): PackageManagerResult {
        packageManagerRepository.deleteAllApps()
        return packageManagerRepository.classifyAndStoreApps()
    }
}