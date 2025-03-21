package com.example.wbsdpmptsp.ui.auth.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.ViewModelFactory
import com.example.wbsdpmptsp.ui.component.CustomButton
import com.example.wbsdpmptsp.ui.component.CustomTextField
import com.example.wbsdpmptsp.ui.component.PasswordField
import com.example.wbsdpmptsp.utils.Result

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current)
)) {
    val context = LocalContext.current
    val loginResult by viewModel.loginResult.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(initial = false)
    val scrollState = rememberScrollState()

    var passwordVisible by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.wbs),
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A237E)
            )

            Text(
                text = stringResource(R.string.kota).uppercase(),
                fontSize = 16.sp,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A237E)
            )
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.logo_clear),
                contentDescription = stringResource(R.string.logo_clear),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            LaunchedEffect(loginResult) {
                loginResult?.let { result ->
                    when (result) {
                        is Result.Success -> {
                            Toast.makeText(context, context.getString(R.string.login_success), Toast.LENGTH_SHORT).show()
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                        is Result.Error -> {
                            Toast.makeText(context, context.getString(R.string.email_or_password_wrong), Toast
                                .LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            CustomTextField(value = email, onValueChange = { email = it }, label = stringResource
                (R.string.email))
            Spacer(modifier = Modifier.height(10.dp))

            PasswordField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                isVisible = passwordVisible,
                onVisibilityChange = { passwordVisible = !passwordVisible },
                imeAction = ImeAction.Done,
                isRequired = false
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = stringResource(R.string.login),
                onClick = { viewModel.login(email, password) },
                enabled = (isLoading == false) && email.isNotBlank() && password.isNotBlank()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.forgot_password),
                modifier = Modifier
                    .clickable {
                        navController.navigate("forgot_password")
                    }
                    .padding(vertical = 8.dp),
                color = Color.Black
            )
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            CustomButton(
                text = stringResource(R.string.not_registered),
                onClick = { navController.navigate("register") },
                backgroundColor = Color(0xFF4A90E2)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.logo_dpmptsp),
                contentDescription = stringResource(R.string.logo),
                modifier = Modifier.width(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = NavHostController(LocalContext.current))
}