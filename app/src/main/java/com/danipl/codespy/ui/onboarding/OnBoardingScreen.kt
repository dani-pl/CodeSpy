package com.danipl.codespy.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.danipl.codespy.R
import com.danipl.codespy.ui.LoadingScreen
import com.danipl.codespy.ui.PagerIndicator
import kotlinx.coroutines.launch

@Composable
fun OnboardingRoute(
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
        OnBoardingState.UiState.ShowInstructions -> {
            OnboardingScreen(
                onOnboardingFinished = viewModel::onOnboardingFinished
            )
        }
        OnBoardingState.UiState.Error -> {
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
                message = stringResource(id = R.string.scanning_apps)
            )
        }
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onOnboardingFinished: () -> Unit
) {
    Scaffold { paddingValues ->
        OnboardingContent(
            modifier = Modifier.padding(paddingValues),
            onOnboardingFinished = onOnboardingFinished
        )
    }
}

@Composable
fun OnboardingContent(
    modifier: Modifier,
    onOnboardingFinished: () -> Unit
) {

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val scrollScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 50.dp,
                top = 50.dp
            )
    ) {
        val (horizontalPager, pagerIndicatorAndNavigationArrows) = createRefs()


        HorizontalPager (
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(horizontalPager) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top)
                    bottom.linkTo(pagerIndicatorAndNavigationArrows.top)
                }
        ) { page: Int ->

            val title = stringResource( id = when(page){
                0 -> R.string.onboarding_page1_title
                1 -> R.string.onboarding_page2_title
                else -> R.string.onboarding_page3_title
            })

            val subtitle = stringResource( id = when(page){
                0 -> R.string.onboarding_page1_subtitle
                1 -> R.string.onboarding_page2_subtitle
                else -> R.string.onboarding_page3_subtitle
            })


            val drawableResId = when(page) {
                0 -> R.drawable.waving_illustration
                1 -> R.drawable.mobile_search_illustration
                else -> R.drawable.approve_illustration
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(100.dp))

                Image(
                    modifier = Modifier.requiredHeight(300.dp),
                    painter = painterResource(id = drawableResId),
                    contentDescription = ""
                )


                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                if(page == 2) {
                    Text(
                        text = buildAnnotatedString {
                            withLink(
                                LinkAnnotation.Url(
                                    stringResource(R.string.code_repository_url),
                                    TextLinkStyles(style = SpanStyle(color = Blue))             )
                            ) {
                                append(stringResource(R.string.code_repository_url))
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        // Pager Indicator and Navigation Arrows

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(pagerIndicatorAndNavigationArrows) {
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                }
                .padding(top = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            // Left arrow or empty
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pagerState.currentPage > 0) {
                    IconButton(
                        onClick = {
                            scrollScope.launch {
                                pagerState.scrollToPage(pagerState.currentPage - 1)
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            }

            PagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.align(Alignment.Center))


            // Right arrow or scan button

            Row(
                modifier = Modifier
                    .align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                when(pagerState.currentPage) {
                    2 ->  {
                        Button(
                            onClick = onOnboardingFinished,
                        ) {
                            Text(
                                text = stringResource(R.string.scan)
                            )
                        }
                    }
                    else -> {
                        IconButton(
                            onClick = {
                                scrollScope.launch {
                                    pagerState.scrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingScreenPreview() {
    Surface {
        OnboardingScreen(
            onOnboardingFinished = { }
        )
    }
}