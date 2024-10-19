package com.danipl.codespy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.danipl.codespy.data.PackageManagerRepository
import com.danipl.codespy.ui.CodeSpyNavigation
import com.danipl.codespy.ui.home.HomeRoute
import com.danipl.codespy.ui.theme.CodeSpyTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var packageManagerRepository: PackageManagerRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeSpyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CodeSpyNavigation()
                }
            }
        }
    }
}