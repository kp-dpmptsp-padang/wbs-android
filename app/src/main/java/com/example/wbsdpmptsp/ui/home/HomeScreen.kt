package com.example.wbsdpmptsp.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.component.BottomNav
import com.example.wbsdpmptsp.ui.component.CustomButton
import com.example.wbsdpmptsp.ui.component.ExpandableCard
import com.example.wbsdpmptsp.ui.theme.primaryBlue

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 66.dp)
                .verticalScroll(scrollState),
        ) {
            Image(
                painter = painterResource(id = R.drawable.hero),
                contentDescription = stringResource(R.string.hero_image),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(207.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.what_is_clear),
                        fontSize = 16.sp,
                        color = primaryBlue,
                        fontWeight = Bold,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(top = 20.dp, bottom = 8.dp)
                    )
                    Text(
                        text = stringResource(R.string.clear_text),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                            .padding(bottom = 20.dp),
                    )
                }
            }

            CustomButton(
                text = stringResource(R.string.start_report),
                onClick = {
                    navController.navigate("report")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(64.dp)
            )

            ExpandableCard(
                title = stringResource(R.string.violation_type),
                content = {
                    Column {
                        Text(stringResource(R.string.pungli))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.sop))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.gratifikasi))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.penyalahgunaan))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.layanan))
                    }
                }
            )

            ExpandableCard(
                title = stringResource(R.string.how_to_report),
                content = {
                    Column {
                        Text(stringResource(R.string.how_to_report_text1))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.how_to_report_text2))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.how_to_report_text3))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.how_to_report_text4))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.how_to_report_text5))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(stringResource(R.string.how_to_report_text6))
                    }
                }
            )

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color.Red)
            ) {
                Row {
                     Image(
                        painter = painterResource(id = R.drawable.ic_warn_red),
                        contentDescription = stringResource(R.string.icon),
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp)
                    )
                    Column (
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.anonymous),
                            fontSize = 14.sp,
                            color = Color.Red
                        )
                        Text(
                            text = stringResource(R.string.identity_secret),
                            fontSize = 12.sp,
                        )
                    }
                }
            }

            ExpandableCard(
                title = stringResource(R.string.tracking),
                content = {
                    CustomButton(
                        text = stringResource(R.string.tracking),
                        onClick = {
                            navController.navigate("track-report")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    )
                }
            )

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, color = primaryBlue)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.need_help),
                        fontWeight = Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 16.dp, bottom = 2.dp)
                    )
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = stringResource(R.string.email),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp, top = 4.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = stringResource(R.string.email_dpmptsp),
                            fontSize = 14.sp,
                            color = primaryBlue,
                            modifier = Modifier
                                .padding(top = 4.dp)
                        )
                    }
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_wa),
                            contentDescription = stringResource(R.string.icon),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 16.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = stringResource(R.string.phone_dpmptsp),
                            fontSize = 14.sp,
                            color = primaryBlue,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(64.dp))
        }

        BottomNav(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            navController = navController
        )
    }
}

@Preview (showBackground = true, showSystemUi = true, device = Devices.PIXEL_6A)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = NavHostController(LocalContext.current))
}