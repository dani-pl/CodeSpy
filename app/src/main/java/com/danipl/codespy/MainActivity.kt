package com.danipl.codespy

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.ui.CodeSpyNavigation
import com.danipl.codespy.ui.theme.CodeSpyTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Inject
    lateinit var packageManagerRepository: PackageManagerRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        var hasUserCompletedOnBoarding = false

        lifecycleScope.launch {
            hasUserCompletedOnBoarding = dataStore.data.map {
                    preferences -> preferences[HAS_USER_COMPLETED_ONBOARDING]
            }.first() ?: false
        }.invokeOnCompletion {
            setContent {
                CodeSpyTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        CodeSpyNavigation(
                            hasUserCompletedOnBoarding = hasUserCompletedOnBoarding
                        )
                    }
                }
            }
        }
    }
}