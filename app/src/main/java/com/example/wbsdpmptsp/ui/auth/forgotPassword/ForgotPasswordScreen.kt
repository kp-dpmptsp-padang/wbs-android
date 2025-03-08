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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
fun ForgotPasswordScreen(
    navController: NavHostController,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val forgotPasswordResult by viewModel.forgotPasswordResult.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val context = LocalContext.current

    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, end = 8.dp)
                .clickable { onBack() }
        )

        var email by remember { mutableStateOf("") }
        CustomTitle(
            title = stringResource(id = R.string.cari_akun),
            modifier
                .padding(vertical = 16.dp)
                .align(Alignment.Start)
        )

        Text(
            text = stringResource(R.string.masukkan_email),
            color = Color.Gray,
            modifier = modifier.padding(bottom = 8.dp),
            fontSize = 12.sp
        )

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(id = R.string.email),
            modifier = modifier.fillMaxWidth()
        )

        Spacer(modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(
                modifier.padding(8.dp),
                color = Color(0xFF4A90E2)
            )
        } else {
            CustomButton(
                text = stringResource(R.string.kirim),
                onClick = { viewModel.forgotPassword(email) },
                enabled = email.isNotBlank()
            )
        }

        LaunchedEffect(forgotPasswordResult) {
            forgotPasswordResult?.let { result ->
                when (result) {
                    is Result.Success -> {
                        Toast.makeText(
                            context,
                            context.getString(R.string.sending_code),
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate("input_code?email=$email") {
                            popUpTo("forgot_password") { inclusive = true }
                        }
                    }
                    is Result.Error -> {
                        Toast.makeText(
                            context,
                            result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {}
                }
            }
        }

        Spacer(modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(navController = NavHostController(LocalContext.current), onBack = {})
}