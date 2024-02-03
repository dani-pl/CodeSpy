package com.danipl.frameworkguesser

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(
        HomeState(
            appName = "Outlook",
            isReactNativeApp = false
        )
    )
    val state = _state.asStateFlow()
}

data class HomeState(val appName: String, val isReactNativeApp: Boolean)