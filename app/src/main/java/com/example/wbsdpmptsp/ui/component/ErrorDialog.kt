package com.example.wbsdpmptsp.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.wbsdpmptsp.R

@Composable
fun ErrorDialog(message: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.error_title),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center)
        },
        text = {
            Text(text = message)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = android.R.string.ok))
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}
