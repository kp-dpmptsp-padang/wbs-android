package com.example.wbsdpmptsp.ui.report.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.component.CustomButton
import com.example.wbsdpmptsp.ui.theme.secondaryBlue

@Composable
fun SuccessScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = secondaryBlue, shape = RoundedCornerShape(
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                )
                .height(250.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_success),
                contentDescription = null,
                modifier = Modifier.size(125.dp),
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.success),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF1A237E)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(40.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.thanks),
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        CustomButton(
                            text = stringResource(R.string.beranda),
                            onClick = { navController.navigate("home") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            textStyle = TextStyle(fontSize = 13.sp)
                        )
                        CustomButton(
                            text = stringResource(R.string.report),
                            onClick = { navController.navigate("history") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            textStyle = TextStyle(fontSize = 13.sp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SuccessScreenPreview() {
    SuccessScreen(navController = NavController(LocalContext.current))
}
