package com.example.wbsdpmptsp.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wbsdpmptsp.R
import com.example.wbsdpmptsp.ui.component.CustomTitle
import com.example.wbsdpmptsp.ui.theme.secondaryBlue

@Composable
fun AboutScreen(
    onBack: () -> Unit,
) {
    Column (
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Row {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = 16.dp, end = 8.dp)
                    .clickable { onBack() }
            )
            CustomTitle(title = stringResource(id = R.string.about))
        }
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_dpmptsp),
                contentDescription = stringResource(id = R.string.logo),
                modifier = Modifier
                    .padding(top = 64.dp)
                    .align(Alignment.CenterHorizontally)
                    .width(200.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.logo_clear),
                contentDescription = stringResource(id = R.string.logo_clear),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
            )
            Text(
                text = stringResource(R.string.version),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = secondaryBlue,
                fontSize = 12.sp
            )
            Text(
                text = stringResource(R.string.explain_ver),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_6A)
@Composable
fun AboutScreenPreview() {
    AboutScreen(onBack = {})
}