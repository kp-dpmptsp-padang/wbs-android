package com.example.wbsdpmptsp.ui.auth.forgotPassword

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.ViewModelFactory
import com.example.wbsdpmptsp.ui.component.CustomButton
import com.example.wbsdpmptsp.ui.component.CustomTextField
import com.example.wbsdpmptsp.ui.component.CustomTitle
import com.example.wbsdpmptsp.utils.Result

@Composable
fun InputCodeScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    email: String,
    navController: NavHostController,
    viewModel: ForgotPasswordViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val context = LocalContext.current
    val verifyCodeResult by viewModel.verifyCodeResult.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    var code by remember { mutableStateOf("") }

    verifyCodeResult?.let { result ->
        when (result) {
            is Result.Success -> {
                LaunchedEffect(result) {
                    navController.navigate("reset_password?email=$email&code=$code") {
                        popUpTo("input_code?email=$email") { inclusive = true }
                    }
                }
            }
            is Result.Error -> {
                LaunchedEffect(result) {
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }
    }

    Column (
        modifier
            .padding(horizontal = 24.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier
                .padding(top = 16.dp, end = 8.dp)
                .clickable { onBack() }
        )

        CustomTitle(
            title = stringResource(id = R.string.konfirm_akun),
            modifier
                .padding(vertical = 16.dp)
        )

        Text(
            text = stringResource(R.string.code),
            color = Color.Gray,
            modifier = modifier
                .padding(bottom = 8.dp),
            fontSize = 12.sp
        )

        CustomTextField(
            value = code,
            onValueChange = { code = it },
            label = stringResource(id = R.string.code_verif),
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier.height(16.dp))

        CustomButton(
            text = stringResource(R.string.kirim),
            onClick = {
                viewModel.verifyCode(email, code)
            },
            enabled = !isLoading && code.isNotBlank()
        )

        Spacer(modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun InputCodeScreenPreview() {
    InputCodeScreen(onBack = {}, email = "", navController = NavHostController(LocalContext.current))
}