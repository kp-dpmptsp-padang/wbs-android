package com.example.wbsdpmptsp.data.remote.retro

import com.example.wbsdpmptsp.data.remote.request.LoginRequest
import com.example.wbsdpmptsp.data.remote.request.LogoutRequest
import com.example.wbsdpmptsp.data.remote.request.RegisterRequest
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import com.example.wbsdpmptsp.data.remote.response.HistoryResponse
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.data.remote.response.ProfileResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @POST("auth/logout")
    suspend fun logout(
        @Header("Authorization") authHeader: String,
        @Body request: LogoutRequest
    ): Response<MessageResponse>

    @GET("auth/profile")
    suspend fun profile(
        @Header("Authorization") authHeader: String
    ): Response<ProfileResponse>

    @GET("reports/history")
    suspend fun history(
        @Header("Authorization") authHeader: String
    ): Response<HistoryResponse>
}