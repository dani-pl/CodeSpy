package com.danipl.frameworkguesser

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        appName = state.appName,
        isReactNativeApp = state.isReactNativeApp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    appName: String,
    isReactNativeApp: Boolean
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.home_screen_title)) }) }
    ) { paddingValues ->
        HomeContent(
            modifier = Modifier.padding(paddingValues),
            appName = appName,
            isReactNativeApp = isReactNativeApp
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier,
    appName: String,
    isReactNativeApp: Boolean
) {
    val isReactNativeAppExplanation =
        stringResource(
            id = if (isReactNativeApp) {
                R.string.app_is_react_native_explanation
            } else {
                R.string.app_is_not_react_native_explanation
            },
            appName
        )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = isReactNativeAppExplanation)
    }
}