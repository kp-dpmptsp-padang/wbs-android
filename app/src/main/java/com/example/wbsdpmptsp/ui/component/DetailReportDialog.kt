package com.example.wbsdpmptsp.ui.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.wbsdpmptsp.BuildConfig
import com.example.wbsdpmptsp.data.remote.response.DetailReportResponse
import com.example.wbsdpmptsp.ui.theme.primaryBlue
import com.example.wbsdpmptsp.R

@Composable
fun DetailReportDialog(
    detailReport: DetailReportResponse?,
    onDismiss: () -> Unit,
    isLoading: Boolean
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(primaryBlue, primaryBlue.copy(alpha = 0.7f))
                            ),
                            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        )
                        .padding(vertical = 16.dp, horizontal = 20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.report_detail),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(28.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.2f),
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = primaryBlue)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(id = R.string.loading_data),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                } else if (detailReport?.data != null) {
                    val reportData = detailReport.data

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .padding(top = 16.dp)
                            .heightIn(max = 500.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = reportData.title ?: "No Title",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                HorizontalDivider(
                                    modifier = Modifier.fillMaxWidth(),
                                    thickness = 1.dp,
                                    color = Color.LightGray.copy(alpha = 0.7f)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            val statusColor = when(reportData.status) {
                                "menunggu-verifikasi" -> Color(0xFFFFA000)
                                "diproses" -> Color(0xFF2196F3)
                                "ditolak" -> Color(0xFFF44336)
                                "selesai" -> Color(0xFF4CAF50)
                                else -> Color.Gray
                            }

                            val statusText = when(reportData.status) {
                                "menunggu-verifikasi" -> "Menunggu Verifikasi"
                                "diproses" -> "Diproses"
                                "ditolak" -> "Ditolak"
                                "selesai" -> "Selesai"
                                else -> reportData.status ?: "Unknown"
                            }

                            Card(
                                colors = CardDefaults.cardColors(containerColor = statusColor.copy(alpha = 0.1f)),
                                border = BorderStroke(1.dp, statusColor),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(color = statusColor, shape = CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = statusText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = statusColor
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    reportData.reporter?.let { reporter ->
                                        InfoRow(
                                            label = "Pelapor",
                                            value = if (reportData.isAnonymous == true) "Anonymous" else reporter.name.toString(),
                                            icon = R.drawable.ic_name
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    reportData.date?.let { dateString ->
                                        val parsedDate = try {
                                            parseDate(dateString)
                                        } catch (e: Exception) {
                                            null
                                        }

                                        InfoRow(
                                            label = "Tanggal",
                                            value = if (parsedDate != null) formatDate(parsedDate) else dateString,
                                            icon = R.drawable.ic_calendar
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    reportData.location?.let { location ->
                                        InfoRow(
                                            label = "Lokasi",
                                            value = location,
                                            icon = R.drawable.ic_location
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    reportData.violation?.let { violation ->
                                        InfoRow(
                                            label = "Pelanggaran",
                                            value = violation,
                                            icon = R.drawable.ic_violation
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    reportData.actors?.let { actors ->
                                        InfoRow(
                                            label = "Pelaku",
                                            value = actors,
                                            icon = R.drawable.ic_actor
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    reportData.processor?.let { processor ->
                                        if (reportData.status != "menunggu-verifikasi") {
                                            InfoRow(
                                                label = "Processor",
                                                value = processor.name ?: "Unknown",
                                                icon = R.drawable.ic_admin
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            if (reportData.status == "ditolak" && !reportData.rejectionReason.toString().isEmpty()) {
                                SectionCard(
                                    title = stringResource(R.string.rejection_reason),
                                    content = reportData.rejectionReason.toString(),
                                    contentColor = Color.Red,
                                    iconRes = R.drawable.ic_error,
                                    backgroundColor = Color(0xFFFFEBEE)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            if (reportData.status == "selesai" && !reportData.adminNotes.toString().isEmpty()) {
                                SectionCard(
                                    title = stringResource(R.string.admin_notes),
                                    content = reportData.adminNotes.toString(),
                                    iconRes = R.drawable.ic_document,
                                    backgroundColor = Color(0xFFE3F2FD)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                            SectionCard(
                                title = stringResource(R.string.description),
                                content = reportData.detail ?: stringResource(R.string.no_description_available),
                                iconRes = R.drawable.ic_desc,
                                backgroundColor = Color(0xFFF5F5F5)
                            )

                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        if (!reportData.files.isNullOrEmpty()) {
                            items(reportData.files) { file ->
                                FileAttachmentItem(file?.filePath.toString())
                            }
                        } else {
                            item {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_no_data),
                                            contentDescription = null,
                                            tint = Color.Gray,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = stringResource(R.string.no_attachments),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_no_data),
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = stringResource(R.string.no_data_available),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, icon: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = primaryBlue,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}

@Composable
fun SectionCard(
    title: String,
    content: String,
    iconRes: Int,
    backgroundColor: Color = Color(0xFFF5F5F5),
    contentColor: Color = Color.Black
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = primaryBlue,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = primaryBlue
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor
            )
        }
    }
}

@Composable
fun FileAttachmentItem(filePath: String) {
    val context = LocalContext.current
    val baseUrl = BuildConfig.FILE_URL
    val fullUrl = baseUrl + filePath.replace("\\", "/")

    val isImage = fullUrl.lowercase().endsWith(".jpg") ||
            fullUrl.lowercase().endsWith(".jpeg") ||
            fullUrl.lowercase().endsWith(".png") ||
            fullUrl.lowercase().endsWith(".gif")

    val isPdf = fullUrl.lowercase().endsWith(".pdf")

    val fileName = filePath.substringAfterLast("/")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                try {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(fullUrl)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        when {
            isImage -> {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = fullUrl)
                                    .crossfade(true)
                                    .build()
                            ),
                            contentDescription = "Attachment image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFEEEEEE))
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_image),
                                contentDescription = null,
                                tint = primaryBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(id = R.string.tap_to_preview),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }
            isPdf -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_doc),
                        contentDescription = "PDF file",
                        tint = Color.Red,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = fileName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(id = R.string.tap_to_preview),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_doc),
                        contentDescription = "File",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = stringResource(id = R.string.attachments),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(id = R.string.tap_to_preview),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}