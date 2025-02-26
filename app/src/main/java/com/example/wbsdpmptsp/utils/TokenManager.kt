package com.example.wbsdpmptsp.utils

import android.content.Context
import android.util.Log
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.response.DataToken
import com.example.wbsdpmptsp.data.remote.response.RefreshTokenRequest
import com.example.wbsdpmptsp.data.remote.response.TokenResponse
import com.example.wbsdpmptsp.data.remote.retro.ApiConfig
import kotlinx.coroutines.flow.first

class TokenManager(private val context: Context, private val userPreference: UserPreference) {

    suspend fun getAccessToken(): String? {
        return userPreference.getAccessToken().first()
    }

    suspend fun saveAccessToken(token: String) {
        userPreference.saveAccessToken(TokenResponse(DataToken(accessToken = token)))
    }

    suspend fun refreshToken(): TokenResponse? {
        val refreshToken = userPreference.getRefreshToken().first()

        if (refreshToken == null) {
            Log.d("TokenManager", "No refresh token available")
            return null
        }

        val response = ApiConfig.createApiService(context).refreshToken(RefreshTokenRequest(refreshToken))
        return if (response.isSuccessful) response.body() else null
    }
}

