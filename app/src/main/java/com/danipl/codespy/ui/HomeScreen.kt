package com.danipl.codespy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.danipl.codespy.R
import com.danipl.codespy.domain.models.UserApp
import com.danipl.codespy.util.ui.LoadImageWithPlaceholderAndError

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        reactNativeApps = state.reactNativeApps,
        cordovaApps = state.cordovaApps,
        flutterApps = state.flutterApps,
        unclassifiedApps = state.unclassifiedApps
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    reactNativeApps: List<UserApp>,
    cordovaApps: List<UserApp>,
    flutterApps: List<UserApp>,
    unclassifiedApps: List<UserApp>
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.home_screen_title)) }) }
    ) { paddingValues ->
        HomeContent(
            modifier = Modifier.padding(paddingValues),
            reactNativeApps = reactNativeApps,
            cordovaApps = cordovaApps,
            flutterApps = flutterApps,
            unclassifiedApps = unclassifiedApps
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    modifier: Modifier,
    reactNativeApps: List<UserApp>,
    cordovaApps: List<UserApp>,
    flutterApps: List<UserApp>,
    unclassifiedApps: List<UserApp>
) {

    val homeScreenSegmentedButtons = listOf(
        SegmentedButton(R.string.seg_button_react_native, R.drawable.ic_react_native),
        SegmentedButton(R.string.seg_button_flutter, R.drawable.ic_flutter),
        SegmentedButton(R.string.seg_button_cordova, R.drawable.ic_cordova)
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var selectedIndex by remember { mutableStateOf(0) }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 10.dp)
            ) {
                homeScreenSegmentedButtons.forEachIndexed { index, segButton ->
                    SegmentedButton(
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = homeScreenSegmentedButtons.size),
                        onClick = { selectedIndex = index },
                        selected = index == selectedIndex
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(segButton.textResId))
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(segButton.iconResId),
                                contentDescription = "",
                                modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }
        }

        val listToShow = when(selectedIndex) {
            0 ->  reactNativeApps
            1 ->  cordovaApps
            2 ->  flutterApps
            else -> unclassifiedApps
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            listToShow.forEach {
                ListItem(
                    headlineContent = { Text(text = it.name) },
                    leadingContent = {
                        LoadImageWithPlaceholderAndError(it.iconUri)
                    }
                )
            }
        }
    }
}