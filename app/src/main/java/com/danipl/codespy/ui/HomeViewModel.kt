package com.danipl.codespy.ui

import androidx.lifecycle.ViewModel
import com.danipl.codespy.domain.GetCordovaAppsUseCase
import com.danipl.codespy.domain.GetFlutterAppsUseCase
import com.danipl.codespy.domain.GetReactNativeAppsUseCase
import com.danipl.codespy.domain.GetUnclassifiedAppsUseCase
import com.danipl.codespy.domain.models.UserApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getReactNativeAppsUseCase: GetReactNativeAppsUseCase,
    getCordovaAppsUseCase: GetCordovaAppsUseCase,
    getUnclassifiedAppsUseCase: GetUnclassifiedAppsUseCase,
    getFlutterAppsUseCase: GetFlutterAppsUseCase
) : ViewModel() {

    private val reactNativeApps = getReactNativeAppsUseCase()
    private val cordovaApps = getCordovaAppsUseCase()
    private val flutterApps = getFlutterAppsUseCase()
    private val unclassifiedApps = getUnclassifiedAppsUseCase()

    private val _state = MutableStateFlow(
        HomeState(
            reactNativeApps = reactNativeApps,
            cordovaApps = cordovaApps,
            flutterApps = flutterApps,
            unclassifiedApps = unclassifiedApps
        )
    )
    val state = _state.asStateFlow()
}

data class HomeState(
    val reactNativeApps: List<UserApp>,
    val cordovaApps: List<UserApp>,
    val flutterApps: List<UserApp>,
    val unclassifiedApps: List<UserApp>
)