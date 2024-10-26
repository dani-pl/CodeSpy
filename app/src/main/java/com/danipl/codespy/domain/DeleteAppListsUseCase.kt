package com.danipl.codespy.domain

import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.data.PackageManagerResult
import javax.inject.Inject

class DeleteAppListsUseCase @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
) {
    operator fun invoke(): PackageManagerResult = packageManagerRepository.deleteAllApps()
}