package com.danipl.codespy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danipl.codespy.domain.ClassifyAndStoreUserAppsUseCase
import com.danipl.codespy.domain.GetCordovaAppsUseCase
import com.danipl.codespy.domain.GetFlutterAppsUseCase
import com.danipl.codespy.domain.GetReactNativeAppsUseCase
import com.danipl.codespy.domain.GetUnclassifiedAppsUseCase
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
    private val getUnclassifiedAppsUseCase: GetUnclassifiedAppsUseCase,
    private val getFlutterAppsUseCase: GetFlutterAppsUseCase,
    private val classifyAndStoreUserAppsUseCase: ClassifyAndStoreUserAppsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _state = MutableStateFlow(
        HomeState(
            reactNativeApps = listOf(),
            cordovaApps = listOf(),
            flutterApps = listOf(),
            unclassifiedApps = listOf()
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                _state.update {
                    HomeState(
                        reactNativeApps = getReactNativeAppsUseCase(),
                        cordovaApps = getCordovaAppsUseCase(),
                        flutterApps = getFlutterAppsUseCase(),
                        unclassifiedApps = getUnclassifiedAppsUseCase()
                    )
                }
            }
        }
    }


    fun classifyAndStoreUserApps() {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                classifyAndStoreUserAppsUseCase()
                _state.update {
                    HomeState(
                        reactNativeApps = getReactNativeAppsUseCase(),
                        cordovaApps = getCordovaAppsUseCase(),
                        flutterApps = getFlutterAppsUseCase(),
                        unclassifiedApps = getUnclassifiedAppsUseCase()
                    )
                }
            }
        }
    }
}

data class HomeState(
    val reactNativeApps: List<UserApp>,
    val cordovaApps: List<UserApp>,
    val flutterApps: List<UserApp>,
    val unclassifiedApps: List<UserApp>
)