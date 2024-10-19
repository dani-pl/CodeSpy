package com.danipl.codespy.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.data.PackageManagerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val packageManagerRepository: PackageManagerRepository
): ViewModel() {

    private val _state = MutableStateFlow<OnBoardingState.UiState>(OnBoardingState.UiState.ShowInstructions(pageNumber = 0))
    val state = _state.asStateFlow()

    private var _events: MutableSharedFlow<OnBoardingState.Event> = MutableSharedFlow()
    val events = _events.asSharedFlow()

    fun onNextClicked() {

    }

    fun onPreviousClicked() {


    }

    fun onOnboardingFinished() {
        _state.update { OnBoardingState.UiState.Loading }

        var packageManagerResult: PackageManagerResult = PackageManagerResult.Error

        viewModelScope.launch {
            packageManagerResult = withContext(Dispatchers.IO) {
                packageManagerRepository.classifyAndStoreApps()
            }
        }.invokeOnCompletion {
            when(packageManagerResult) {
                PackageManagerResult.Success -> {
                    viewModelScope.launch {
                        _events.emit(OnBoardingState.Event.OnClassifyAndStoreAppsFinished)
                    }
                }
                PackageManagerResult.Error -> _state.update { OnBoardingState.UiState.Error }
            }
        }
    }

}

sealed class OnBoardingState {

    sealed class UiState {
        data class ShowInstructions(val pageNumber: Int): UiState()
        data object Error: UiState()
        data object Loading: UiState()
    }


    sealed class Event {
        data object OnClassifyAndStoreAppsFinished : Event()
    }
}