package com.example.wbsdpmptsp.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wbsdpmptsp.ui.theme.primaryBlue

@Composable
fun CustomTitle(
    title: String,
    modifier: Modifier = Modifier
        .padding(horizontal = 16.dp)
        .padding(top = 16.dp)
) {
    Text(
        text = title,
        fontSize = 20.sp,
        color = primaryBlue,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}