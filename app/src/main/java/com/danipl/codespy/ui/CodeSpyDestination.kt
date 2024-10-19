package com.danipl.codespy.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danipl.codespy.ui.home.HomeRoute
import com.danipl.codespy.ui.onboarding.OnboardingRoute
import kotlinx.serialization.Serializable

@Composable
fun CodeSpyNavigation(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CodeSpyDestination.Onboarding
    ) {
        composable<CodeSpyDestination.Onboarding> {
            OnboardingRoute(
                navigateToHomeScreen = { navController.navigate(CodeSpyDestination.Home) },
                viewModel = hiltViewModel()
            )
        }
        composable<CodeSpyDestination.Home> {
            HomeRoute(
                viewModel = hiltViewModel()
            )
        }

    }
}

@Serializable
sealed class CodeSpyDestination {

    @Serializable
    data object Onboarding

    @Serializable
    data object Home
}