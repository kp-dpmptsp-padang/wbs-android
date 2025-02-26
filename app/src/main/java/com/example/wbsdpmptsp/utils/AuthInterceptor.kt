package com.example.wbsdpmptsp.utils

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenManager.getAccessToken() }
        var request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        val response = chain.proceed(request)
        val responseBody = response.peekBody(Long.MAX_VALUE).string()
        if (response.code == 401 || (response.code == 400 && responseBody.contains("Invalid token"))) {
            response.close()

            val newToken = runBlocking { tokenManager.refreshToken() }
            if (newToken != null) {
                runBlocking { tokenManager.saveAccessToken(newToken.data?.accessToken.toString()) }

                val updatedToken = runBlocking { tokenManager.getAccessToken() }

                request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $updatedToken")
                    .build()
                return chain.proceed(request)
            }
        }

        return response
    }
}


