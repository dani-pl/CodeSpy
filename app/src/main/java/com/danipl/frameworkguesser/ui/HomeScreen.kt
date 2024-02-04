package com.danipl.frameworkguesser.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danipl.frameworkguesser.R
import com.danipl.frameworkguesser.domain.models.AppInfo
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        apps = state.apps
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    apps: List<AppInfo>
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.home_screen_title)) }) }
    ) { paddingValues ->
        HomeContent(
            modifier = Modifier.padding(paddingValues),
            apps = apps
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier,
    apps: List<AppInfo>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        apps.forEach {
            ListItem(
                headlineContent = { Text(text = it.name) },
                leadingContent = {
                    Image(
                        painter = rememberDrawablePainter(drawable = it.icon),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                }
            )
        }
    }
}