package com.example.wbsdpmptsp.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.ViewModelFactory
import com.example.wbsdpmptsp.ui.component.ChatBubble
import com.example.wbsdpmptsp.ui.component.ChatContent
import com.example.wbsdpmptsp.ui.component.ChatInputField
import com.example.wbsdpmptsp.ui.component.CustomTitle
import com.example.wbsdpmptsp.ui.component.InfoDialog
import com.example.wbsdpmptsp.ui.theme.primaryBlue
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun ChatScreen(
    uniqueCode: String,
    onBack: () -> Unit,
    viewModel: ChatViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    val chatState by viewModel.chat.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState(null)
    var showInfoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uniqueCode) {
        viewModel.getAnonymChat(uniqueCode)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        modifier = Modifier
                            .padding(top = 16.dp, end = 8.dp)
                            .clickable { onBack() }
                    )
                    CustomTitle(title = stringResource(id = R.string.chat_dengan_admin))
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_track_anon),
                    contentDescription = "Chat",
                    tint = primaryBlue,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.unique),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Info",
                tint = primaryBlue,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(20.dp)
                    .clickable { showInfoDialog = true }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (!errorMessage.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = errorMessage.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            } else {
                ChatContent(
                    chats = chatState ?: emptyList(),
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (showInfoDialog) {
                InfoDialog(
                    onDismiss = { showInfoDialog = false },
                    uniqueCode = uniqueCode
                )
            }
        }

        ChatInputField(
            onSendMessage = { message ->
//                viewModel.sendAnonymChat(uniqueCode, message)
            }
        )
    }
}

fun formatTime(timestamp: String?): String {
    if (timestamp == null) return ""

    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()

        val date = inputFormat.parse(timestamp)
        if (date != null) {
            outputFormat.format(date)
        } else {
            ""
        }
    } catch (_: Exception) {
        timestamp.split("T").getOrNull(1)?.split(".")?.getOrNull(0)?.substring(0, 5) ?: ""
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen(
        uniqueCode = "ABC123",
        onBack = { /* Preview action */ }
    )
}

@Preview(showBackground = true)
@Composable
fun ChatBubblePreview() {
    ChatBubble(
        message = "Halo, ada yang bisa saya bantu?",
        time = "12:00",
        isAdmin = false,
        senderName = "Admin"
    )
}

@Preview(showBackground = true)
@Composable
fun InfoDialogPreview() {
    InfoDialog(
        onDismiss = { /* Preview action */ },
        uniqueCode = "ABC123"
    )
}