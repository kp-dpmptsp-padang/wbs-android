package com.example.wbsdpmptsp.data.remote.retro

import com.example.wbsdpmptsp.data.remote.request.LoginRequest
import com.example.wbsdpmptsp.data.remote.request.LogoutRequest
import com.example.wbsdpmptsp.data.remote.request.RegisterRequest
import com.example.wbsdpmptsp.data.remote.request.ReportRequest
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import com.example.wbsdpmptsp.data.remote.response.HistoryResponse
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.data.remote.response.NotificationResponse
import com.example.wbsdpmptsp.data.remote.response.ProfileResponse
import com.example.wbsdpmptsp.data.remote.response.RefreshTokenRequest
import com.example.wbsdpmptsp.data.remote.response.ReportResponse
import com.example.wbsdpmptsp.data.remote.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @POST("auth/token")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<TokenResponse>

    @GET("reports/history")
    suspend fun history(
        @Header("Authorization") authHeader: String
    ): Response<HistoryResponse>

    @GET("notifications")
    suspend fun notification(
        @Header("Authorization") authHeader: String
    ): Response<NotificationResponse>

    @PUT("notifications/read-all")
    suspend fun readAllNotification(
        @Header("Authorization") authHeader: String
    ): Response<MessageResponse>

    @PUT("notifications/{id}/read")
    suspend fun readNotification(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int
    ): Response<MessageResponse>

    @POST("reports")
    suspend fun createReport(
        @Header("Authorization") authHeader: String,
        @Body request: ReportRequest
    ): Response<ReportResponse>
}