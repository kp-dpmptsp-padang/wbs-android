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
fun NewPasswordScreen(
    onBack: () -> Unit,
    email: String,
    code: String,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val context = LocalContext.current
    val resetPasswordResult by viewModel.resetPasswordResult.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)

    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordsMatch by remember { mutableStateOf(true) }

    resetPasswordResult?.let { result ->
        when (result) {
            is Result.Success -> {
                LaunchedEffect(result) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.reset_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
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

    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp, end = 8.dp)
                .clickable { onBack() }
        )

        CustomTitle(
            title = stringResource(id = R.string.atur_password),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Text(
            text = stringResource(R.string.new_pw),
            color = Color.Gray,
            modifier = modifier.padding(bottom = 8.dp),
            fontSize = 12.sp
        )

        CustomTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
                passwordsMatch = confirmPassword.isEmpty() || newPassword == confirmPassword
            },
            label = stringResource(id = R.string.password),
            modifier = modifier.fillMaxWidth(),
            isPassword = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                passwordsMatch = newPassword == confirmPassword
            },
            label = stringResource(id = R.string.confirm_password),
            modifier = modifier.fillMaxWidth(),
            isPassword = true,
        )

        if (!passwordsMatch) {
            Text(
                text = stringResource(R.string.donot_match),
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        CustomButton(
            text = stringResource(R.string.kirim),
            onClick = {
                if (newPassword == confirmPassword) {
                    viewModel.resetPassword(email, code, newPassword)
                } else {
                    passwordsMatch = false
                }
            },
            enabled = !isLoading && newPassword.isNotBlank() && confirmPassword.isNotBlank()
        )

        Spacer(modifier = Modifier.height(24.dp))

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewPasswordScreen() {
    NewPasswordScreen(
        onBack = {},
        email = "test@example.com",
        code = "123456",
        navController = NavHostController(LocalContext.current)
    )
}