package com.example.wbsdpmptsp.ui.component

import android.R.attr.label
import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isRequired: Boolean = false,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Row {
                Text(label, color = Color.Black)
                if (isRequired) {
                    Text("*", color = Color.Red)
                }
            }
        },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = Color(0x801A237E)
        ),
        shape = RoundedCornerShape(10.dp),
        maxLines = maxLines,
    )
}

@Composable
fun CustomTextAreaField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isRequired: Boolean = false,
    singleLine: Boolean = false,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    maxChar: Int? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (maxChar == null || newValue.length <= maxChar) {
                onValueChange(newValue)
            }
        },
        label = {
            Row {
                Text(label, color = Color.Black)
                if (isRequired) {
                    Text("*", color = Color.Red)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth(),
        singleLine = singleLine,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = Color(0x801A237E)
        ),
        shape = RoundedCornerShape(10.dp),
        maxLines = maxLines,
        supportingText = maxChar?.let {
            { Text("${value.length}/$maxChar") }
        }
    )
}

@Composable
fun CustomDatePicker(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isRequired: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            onValueChange(format.format(calendar.time))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedTextField(
        value = value,
        onValueChange = { },
        label = {
            Row {
                Text(label, color = Color.Black)
                if (isRequired) {
                    Text("*", color = Color.Red)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() },
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = Color(0x801A237E),
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black
        )
    )
}

@Composable
fun SummaryItem(
    label: String,
    value: String,
    isRequired: Boolean = false,
    isMultiline: Boolean = false,
    isFile: Boolean = false,
    onFilePreview: (() -> Unit)? = null
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row {
            Text(label, color = Color.Black)
            if (isRequired) {
                Text("*", color = Color.Red)
            }
        }
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            singleLine = !isMultiline,
            readOnly = true,
            trailingIcon = if (isFile && onFilePreview != null) {
                {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Preview File",
                        modifier = Modifier.clickable { onFilePreview() }
                    )
                }
            } else null,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color(0x801A237E)
            ),
            shape = RoundedCornerShape(10.dp),
            maxLines = if (isMultiline) 5 else 1
        )
    }
}
