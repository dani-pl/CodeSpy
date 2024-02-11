package com.danipl.codespy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danipl.codespy.domain.GetCordovaAppsUseCase
import com.danipl.codespy.domain.GetReactNativeAppsUseCase
import com.danipl.codespy.domain.GetUnclassifiedAppsUseCase
import com.danipl.codespy.domain.models.AppInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getReactNativeAppsUseCase: GetReactNativeAppsUseCase,
    getCordovaAppsUseCase: GetCordovaAppsUseCase,
    getUnclassifiedAppsUseCase: GetUnclassifiedAppsUseCase
) : ViewModel() {

    private val reactNativeApps = getReactNativeAppsUseCase.getReactNativeApps()
    private val cordovaApps = getCordovaAppsUseCase.getCordovaApps()
    private val unclassifiedApps = getUnclassifiedAppsUseCase.getUnclassifiedApps()

    private val _state = MutableStateFlow(
        HomeState(
            reactNativeApps = reactNativeApps,
            cordovaApps = cordovaApps,
            unclassifiedApps = unclassifiedApps
        )
    )
    val state = _state.asStateFlow()
}

data class HomeState(
    val reactNativeApps: List<AppInfo>,
    val cordovaApps: List<AppInfo>,
    val unclassifiedApps: List<AppInfo>
)