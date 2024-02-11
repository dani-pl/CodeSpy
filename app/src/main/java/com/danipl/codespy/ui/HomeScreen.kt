package com.danipl.codespy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danipl.codespy.R
import com.danipl.codespy.domain.models.AppInfo
import com.google.accompanist.drawablepainter.rememberDrawablePainter

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        reactNativeApps = state.reactNativeApps,
        cordovaApps = state.cordovaApps,
        unclassifiedApps = state.unclassifiedApps
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    reactNativeApps: List<AppInfo>,
    cordovaApps: List<AppInfo>,
    unclassifiedApps: List<AppInfo>
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.home_screen_title)) }) }
    ) { paddingValues ->
        HomeContent(
            modifier = Modifier.padding(paddingValues),
            reactNativeApps = reactNativeApps,
            cordovaApps = cordovaApps,
            unclassifiedApps = unclassifiedApps
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    modifier: Modifier,
    reactNativeApps: List<AppInfo>,
    cordovaApps: List<AppInfo>,
    unclassifiedApps: List<AppInfo>
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectedIndex by remember { mutableStateOf(0) }
        val options = listOf("RN", "Cordova", "Unclassified")

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 10.dp)
            ) {
                options.forEachIndexed { index, label ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                        onClick = { selectedIndex = index },
                        selected = index == selectedIndex
                    ) {
                        Text(text = label)
                    }
                }
            }
        }

        var listToShow = listOf<AppInfo>()
        when(selectedIndex){
            0 -> listToShow = listToShow + reactNativeApps
            1 -> listToShow = listToShow + cordovaApps
            2 -> listToShow = listToShow + unclassifiedApps
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            listToShow.forEach {
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
}