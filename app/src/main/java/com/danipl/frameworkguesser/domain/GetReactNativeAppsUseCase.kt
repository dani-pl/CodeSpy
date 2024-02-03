package com.danipl.frameworkguesser.domain

import com.danipl.frameworkguesser.data.AdbRepository

/**
 * This use case fetches the apps that use react native in the user's phone.
 */
class GetReactNativeAppsUseCase(
    private val AdbRepository: AdbRepository
)