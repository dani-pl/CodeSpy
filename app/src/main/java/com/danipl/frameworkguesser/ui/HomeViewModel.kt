package com.danipl.frameworkguesser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danipl.frameworkguesser.domain.GetReactNativeAppsUseCase
import com.danipl.frameworkguesser.domain.models.AppInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getReactNativeAppsUseCase: GetReactNativeAppsUseCase
) : ViewModel() {

    private var installedReactNativeApps = listOf<AppInfo>()

    init {
        viewModelScope.launch {
            installedReactNativeApps =
                installedReactNativeApps + getReactNativeAppsUseCase.getReactNativeApps()
        }
    }

    private val _state = MutableStateFlow(
        HomeState(
            apps = installedReactNativeApps
        )
    )
    val state = _state.asStateFlow()
}

data class HomeState(val apps: List<AppInfo>)