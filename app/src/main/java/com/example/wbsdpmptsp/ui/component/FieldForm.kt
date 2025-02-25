package com.example.wbsdpmptsp.ui.component

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Black) },
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
        label = { Text(label, color = Color.Black) },
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
        label = { Text(label, color = Color.Black) },
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

