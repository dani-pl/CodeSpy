package com.danipl.codespy.ui.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danipl.codespy.HAS_USER_COMPLETED_ONBOARDING
import com.danipl.codespy.data.PackageManagerResult
import com.danipl.codespy.domain.DeleteAppListsUseCase
import com.danipl.codespy.domain.GetCordovaAppsUseCase
import com.danipl.codespy.domain.GetFlutterAppsUseCase
import com.danipl.codespy.domain.GetReactNativeAppsUseCase
import com.danipl.codespy.domain.RefreshAllAppListsUseCase
import com.danipl.codespy.domain.models.UserApp
import com.danipl.codespy.ui.onboarding.OnBoardingState
import com.danipl.codespy.util.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
    private val refreshAllAppListsUseCase: RefreshAllAppListsUseCase,
    private val deleteAppListsUseCase: DeleteAppListsUseCase,
    private val dataStore: DataStore<Preferences>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {



    private var reactNativeApps = listOf<UserApp>()
    private var flutterApps = listOf<UserApp>()
    private var cordovaApps = listOf<UserApp>()


    private val _state = MutableStateFlow<HomeState>(HomeState.Loading.ReadingFromDatabase)
    val state = _state.asStateFlow()

    private var _events: MutableSharedFlow<HomeState.Event> = MutableSharedFlow()
    val events = _events.asSharedFlow()

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
            HomeState.Success(
                reactNativeApps = reactNativeApps,
                flutterApps = flutterApps,
                cordovaApps = cordovaApps
            )
        }
    }

    fun refreshAppLists() {
        _state.update { HomeState.Loading.ScanningApps }
        var packageManagerResult: PackageManagerResult = PackageManagerResult.Error

        viewModelScope.launch {
            packageManagerResult = withContext(ioDispatcher) { refreshAllAppListsUseCase()}
        }.invokeOnCompletion {
            when(packageManagerResult) {
                PackageManagerResult.Success -> {
                    viewModelScope.launch {
                        populateListsOfAppsPerFramework()
                    }
                }
                PackageManagerResult.Error -> { _state.update { HomeState.Error }}
            }
        }
    }

    fun deleteAppLists() {
        _state.update { HomeState.Loading.DeletingApps }

        var packageManagerResult: PackageManagerResult = PackageManagerResult.Error

        viewModelScope.launch {
            withContext(ioDispatcher) {
                packageManagerResult = deleteAppListsUseCase()
            }
        }.invokeOnCompletion {
            when(packageManagerResult) {
                PackageManagerResult.Success -> {
                    viewModelScope.launch {
                        _events.emit(HomeState.Event.AppsDeletedSuccessfully)
                        dataStore.edit { preferences ->
                            preferences[HAS_USER_COMPLETED_ONBOARDING] = false
                        }
                    }
                }
                PackageManagerResult.Error -> {}
            }
        }



    }


}

sealed class HomeState {
    data class Success(
        val reactNativeApps: List<UserApp>,
        val cordovaApps: List<UserApp>,
        val flutterApps: List<UserApp>
    ) : HomeState()

    data object Error: HomeState()

    sealed class Loading: HomeState() {
        data object ReadingFromDatabase: Loading()
        data object ScanningApps: Loading()
        data object DeletingApps: Loading()
    }

    sealed class Event {
        data object AppsDeletedSuccessfully: Event()
    }
}