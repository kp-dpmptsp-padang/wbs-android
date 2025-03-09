package com.example.wbsdpmptsp.ui.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.ui.theme.primaryBlue
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

data class OnboardingData(
    val title: String,
    val description: String,
    val imageRes: Int,
    val backgroundRes: Int
)

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun WelcomeScreen(
    navController: NavHostController
) {
    val context = LocalContext.current
    val userPreference = UserPreference(context)
    val scope = rememberCoroutineScope()

    val onboardingData = listOf(
        OnboardingData(
            title = stringResource(R.string.welcome_1),
            description = stringResource(R.string.welcome_1_explain),
            imageRes = R.drawable.logo_clear,
            backgroundRes = R.drawable.ic_welcome_1_elemen
        ),
        OnboardingData(
            title = stringResource(R.string.welcome_2),
            description = stringResource(R.string.welcome_2_explain),
            imageRes = R.drawable.ic_kaca,
            backgroundRes = R.drawable.ic_welcome_2_elemen
        ),
        OnboardingData(
            title = stringResource(R.string.welcome_3),
            description = stringResource(R.string.welcome_3_explain),
            imageRes = R.drawable.ic_track_anon,
            backgroundRes = R.drawable.ic_welcome_3_elemen
        )
    )

    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (pagerState.currentPage) {
            0 -> {
                BackgroundCurveBottom(backgroundRes = onboardingData[0].backgroundRes)
            }
            1 -> {
                BackgroundCurveLeft(backgroundRes = onboardingData[1].backgroundRes)
            }
            2 -> {
                BackgroundCurveRight(backgroundRes = onboardingData[2].backgroundRes)
            }
        }

        if (pagerState.currentPage == 0) {
            Image(
                painter = painterResource(id = R.drawable.logo_dpmptsp),
                contentDescription = "Logo DPMPTSP",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(250.dp)
                    .padding(vertical = 48.dp)
            )
        } else {
            Spacer(modifier = Modifier.height(80.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HorizontalPager(
                state = pagerState,
                count = onboardingData.size,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { page ->
                OnboardingPageContent(onboardingData[page])
            }

            OnboardingControls(
                pagerState = pagerState,
                pageCount = onboardingData.size,
                onSkipClick = {
                    scope.launch {
                        userPreference.saveOnboardingCompleted(true)
                        navController.navigate("login") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    }
                },
                onNextClick = {
                    scope.launch {
                        if (pagerState.currentPage < onboardingData.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            userPreference.saveOnboardingCompleted(true)
                            navController.navigate("login") {
                                popUpTo("welcome") { inclusive = true }
                            }
                        }
                    }
                },
                isLastPage = pagerState.currentPage == onboardingData.size - 1
            )
        }
    }
}

@Composable
fun BoxScope.BackgroundCurveBottom(backgroundRes: Int) {
    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Background Curve Bottom",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun BoxScope.BackgroundCurveRight(backgroundRes: Int) {
    Box(
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Background Curve Right",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun BoxScope.BackgroundCurveLeft(backgroundRes: Int) {
    Box(
        modifier = Modifier
            .align(Alignment.CenterStart)
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id = backgroundRes),
            contentDescription = "Background Curve Left",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun OnboardingPageContent(
    data: OnboardingData
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = data.imageRes),
            contentDescription = "Onboarding Image",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 10.dp, top = 16.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = data.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = primaryBlue,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = data.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.DarkGray,
            fontSize = 16.sp,
            lineHeight = 24.sp
        )
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
@Composable
fun OnboardingControls(
    pagerState: PagerState,
    pageCount: Int,
    onSkipClick: () -> Unit,
    onNextClick: () -> Unit,
    isLastPage: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        AnimatedVisibility(
            visible = !isLastPage,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            TextButton(
                onClick = onSkipClick,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = "Lewati",
                    color = primaryBlue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(pageCount) { page ->
                val width by animateDpAsState(
                    targetValue = if (page == pagerState.currentPage) 24.dp else 8.dp,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )

                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(width)
                        .clip(CircleShape)
                        .background(
                            if (page == pagerState.currentPage) primaryBlue
                            else primaryBlue.copy(alpha = 0.5f)
                        )
                )
            }
        }

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(48.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryBlue,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = if (isLastPage) "Mulai" else "Selanjutnya",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navController = NavHostController(LocalContext.current))
}