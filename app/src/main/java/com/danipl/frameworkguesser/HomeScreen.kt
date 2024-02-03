package com.danipl.frameworkguesser

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
internal fun HomeRoute() {
    HomeScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen() {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.home_screen_title)) }) }
    ) { paddingValues ->
        HomeContent(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
    }
}