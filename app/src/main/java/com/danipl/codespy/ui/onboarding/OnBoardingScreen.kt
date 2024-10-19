package com.danipl.codespy.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.danipl.codespy.R
import com.danipl.codespy.ui.LoadingScreen

@Composable
fun OnboardingRoute(
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel,
    navigateToHomeScreen: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is OnBoardingState.Event.OnClassifyAndStoreAppsFinished) navigateToHomeScreen()
        }
    }

    when(state) {
        is OnBoardingState.UiState.ShowInstructions -> {
            OnboardingScreen(
                state = state,
                onNextClicked = viewModel::onNextClicked,
                onPreviousClicked = viewModel::onPreviousClicked,
                onOnboardingFinished = viewModel::onOnboardingFinished,
                navigateToHomeScreen = navigateToHomeScreen
            )
        }
        is OnBoardingState.UiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.generic_error_msg_title))
            }
        }
        OnBoardingState.UiState.Loading -> {
            LoadingScreen(
                message = stringResource(id = R.string.processing_user_apps_loading_mg)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    state: OnBoardingState.UiState,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onOnboardingFinished: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(
            title = { Text(text = "") }
        ) }
    ) { paddingValues ->
        OnboardingContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onNextClicked = onNextClicked,
            onPreviousClicked = onPreviousClicked,
            onOnboardingFinished = onOnboardingFinished,
            navigateToHomeScreen = navigateToHomeScreen
        )
    }
}

@Composable
fun OnboardingContent(
    modifier: Modifier,
    state: OnBoardingState.UiState,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onOnboardingFinished: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = onOnboardingFinished
        ) {
            Text(
                text = "Calculate your apps' frameworks"
            )
        }
    }
}