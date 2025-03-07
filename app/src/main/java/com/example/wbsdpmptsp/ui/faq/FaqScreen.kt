package com.example.wbsdpmptsp.ui.faq

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.component.CustomFaq
import com.example.wbsdpmptsp.ui.component.CustomTitle
import com.example.wbsdpmptsp.ui.theme.primaryBlue
import com.example.wbsdpmptsp.ui.theme.secondaryBlue
import androidx.core.net.toUri

@Composable
fun FaqScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val waNumber = "6281374078088"

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = "https://wa.me/$waNumber".toUri()
                    }
                    context.startActivity(intent)
                },
                containerColor = Color(0xFF25D366)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wa),
                    contentDescription = stringResource(id = R.string.icon),
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = PaddingValues(
                    bottom = paddingValues.calculateBottomPadding()
                ))
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState)
        ) {
            Row {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(top = 16.dp, end = 8.dp)
                        .clickable { onBack() }
                )
                CustomTitle(title = stringResource(id = R.string.help))
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomFaq(
                question = stringResource(R.string.faq_1),
                answer = stringResource(R.string.explanation_1)
            )

            CustomFaq(
                question = stringResource(R.string.faq_2),
                answer = stringResource(R.string.explanation_2)
            )

            CustomFaq(
                question = stringResource(R.string.faq_3),
                answer = stringResource(R.string.explanation_3)
            )

            CustomFaq(
                question = stringResource(R.string.faq_4),
                answer = stringResource(R.string.explanation_4)
            )

            CustomFaq(
                question = stringResource(R.string.faq_5),
                answer = stringResource(R.string.explanation_5)
            )

            CustomFaq(
                question = stringResource(R.string.faq_6),
                answer = stringResource(R.string.explanation_6)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, color = secondaryBlue)
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.need_help_more),
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
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FaqScreenPreview() {
    FaqScreen(onBack = {})
}