package com.example.wbsdpmptsp.data.remote.retro

import com.example.wbsdpmptsp.data.remote.request.LoginRequest
import com.example.wbsdpmptsp.data.remote.request.RegisterRequest
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthResponse>
}