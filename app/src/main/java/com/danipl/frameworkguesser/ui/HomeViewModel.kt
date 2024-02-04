package com.danipl.frameworkguesser.ui

import androidx.lifecycle.ViewModel
import com.danipl.frameworkguesser.domain.GetReactNativeAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getReactNativeAppsUseCase: GetReactNativeAppsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
        HomeState(
            appName = "Outlook",
            isReactNativeApp = false
        )
    )
    val state = _state.asStateFlow()

    fun getReactNativeApps(){
        _state.update {
            it.copy(
                appName = getReactNativeAppsUseCase.getReactNativeApps().toString()
            )
        }
    }
}

data class HomeState(val appName: String, val isReactNativeApp: Boolean)