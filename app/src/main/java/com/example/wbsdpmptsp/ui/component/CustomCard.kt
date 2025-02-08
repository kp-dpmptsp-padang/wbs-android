package com.example.wbsdpmptsp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoSection(
    title: String? = null,
    items: List<Triple<Int, String, String>>,
    backgroundColor: Color = Color(0xFFFFFFFF),
    titleColor: Color = Color(0xFF000000),
    textColor: Color = Color(0xFF1A237E),
    subtextColor: Color = Color(0xFF4A90E2),
    onItemClick: ((Int) -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        title?.let {
            Text(
                text = it,
                fontSize = 16.sp,
                color = titleColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp),
                    spotColor = Color.Gray,
                    ambientColor = Color.Gray
                )
                .background(backgroundColor, RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = Color(0xFF1A237E),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp).padding(horizontal = 8.dp)
        ) {
            items.forEachIndexed { index, (iconRes, text, subtext) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onItemClick?.invoke(index) }
                ) {
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text, fontSize = 14.sp, color = textColor)
                        Text(subtext, fontSize = 12.sp, color = subtextColor)
                    }
                }
            }
        }
    }
}