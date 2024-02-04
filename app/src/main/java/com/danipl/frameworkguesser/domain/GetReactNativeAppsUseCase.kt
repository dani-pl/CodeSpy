package com.danipl.frameworkguesser.domain

import com.danipl.frameworkguesser.data.AdbRepository
import javax.inject.Inject

/**
 * This use case fetches the apps that use react native in the user's phone.
 */
class GetReactNativeAppsUseCase @Inject constructor(
    private val adbRepository: AdbRepository
) {
    fun getReactNativeApps(): List<String> {
        return adbRepository.getReactNativeApps()
    }
}