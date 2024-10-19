package com.danipl.codespy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danipl.codespy.domain.ClassifyAndStoreUserAppsUseCase
import com.danipl.codespy.domain.GetCordovaAppsUseCase
import com.danipl.codespy.domain.GetFlutterAppsUseCase
import com.danipl.codespy.domain.GetReactNativeAppsUseCase
import com.danipl.codespy.domain.models.UserApp
import com.danipl.codespy.util.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getReactNativeAppsUseCase: GetReactNativeAppsUseCase,
    private val getCordovaAppsUseCase: GetCordovaAppsUseCase,
    private val getFlutterAppsUseCase: GetFlutterAppsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {



    private var reactNativeApps = listOf<UserApp>()
    private var flutterApps = listOf<UserApp>()
    private var cordovaApps = listOf<UserApp>()


    private val _state = MutableStateFlow(
        HomeState(
            reactNativeApps =  listOf(),
            cordovaApps = listOf(),
            flutterApps =  listOf(),
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            populateListsOfAppsPerFramework()
        }
    }


    private suspend fun populateListsOfAppsPerFramework() {
        withContext(ioDispatcher) {
            reactNativeApps = getReactNativeAppsUseCase()
            flutterApps = getFlutterAppsUseCase()
            cordovaApps = getCordovaAppsUseCase()
        }

        _state.update {
            HomeState(
                reactNativeApps = reactNativeApps,
                flutterApps = flutterApps,
                cordovaApps = cordovaApps
            )
        }
    }
}

data class HomeState(
    val reactNativeApps: List<UserApp>,
    val cordovaApps: List<UserApp>,
    val flutterApps: List<UserApp>
)