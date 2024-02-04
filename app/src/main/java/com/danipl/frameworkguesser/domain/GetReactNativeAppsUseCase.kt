package com.danipl.frameworkguesser.domain

import com.danipl.frameworkguesser.data.AdbRepository
import com.danipl.frameworkguesser.domain.models.AppInfo
import com.danipl.frameworkguesser.domain.models.toDomain
import javax.inject.Inject

/**
 * This use case fetches the apps that use react native in the user's phone.
 */
class GetReactNativeAppsUseCase @Inject constructor(
    private val adbRepository: AdbRepository
) {
    fun getReactNativeApps(): List<AppInfo> {
        return adbRepository.getReactNativeApps().map {
            it.toDomain()
        }
    }
}