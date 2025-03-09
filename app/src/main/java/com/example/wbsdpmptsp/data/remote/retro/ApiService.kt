package com.example.wbsdpmptsp.data.remote.retro

import com.example.wbsdpmptsp.data.remote.request.ChangePasswordRequest
import com.example.wbsdpmptsp.data.remote.request.ForgotPasswordRequest
import com.example.wbsdpmptsp.data.remote.request.LoginRequest
import com.example.wbsdpmptsp.data.remote.request.LogoutRequest
import com.example.wbsdpmptsp.data.remote.request.RegisterRequest
import com.example.wbsdpmptsp.data.remote.request.ResetPasswordRequest
import com.example.wbsdpmptsp.data.remote.request.SendChatRequest
import com.example.wbsdpmptsp.data.remote.request.UpdateProfileRequest
import com.example.wbsdpmptsp.data.remote.request.VerifyCodeRequest
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import com.example.wbsdpmptsp.data.remote.response.ChatResponse
import com.example.wbsdpmptsp.data.remote.response.DetailReportResponse
import com.example.wbsdpmptsp.data.remote.response.HistoryResponse
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.data.remote.response.NotificationResponse
import com.example.wbsdpmptsp.data.remote.response.ProfileResponse
import com.example.wbsdpmptsp.data.remote.response.RefreshTokenRequest
import com.example.wbsdpmptsp.data.remote.response.ReportResponse
import com.example.wbsdpmptsp.data.remote.response.SendChatResponse
import com.example.wbsdpmptsp.data.remote.response.TokenResponse
import com.example.wbsdpmptsp.data.remote.response.UpdateProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @POST("auth/forgot-password-android")
    suspend fun forgotPassword(
        @Body request : ForgotPasswordRequest
    ): Response<MessageResponse>

    @POST("auth/verify-code")
    suspend fun verifyCode(
        @Body request: VerifyCodeRequest
    ): Response<MessageResponse>

    @POST("auth/reset-password-android")
    suspend fun resetPassword(
        @Body request: ResetPasswordRequest
    ): Response<MessageResponse>

    @GET("auth/profile")
    suspend fun profile(
        @Header("Authorization") authHeader: String
    ): Response<ProfileResponse>

    @PUT("auth/profile")
    suspend fun updateProfile(
        @Header("Authorization") authHeader: String,
        @Body request: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @PUT("auth/password")
    suspend fun updatePassword(
        @Header("Authorization") authHeader: String,
        @Body request: ChangePasswordRequest
    ): Response<MessageResponse>

    @POST("auth/token")
    suspend fun refreshToken(
        @Body request: RefreshTokenRequest
    ): Response<TokenResponse>

    @GET("reports/history")
    suspend fun history(
        @Header("Authorization") authHeader: String
    ): Response<HistoryResponse>

    @GET("reports/{id}")
    suspend fun detailReport(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int
    ): Response<DetailReportResponse>

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

    @Multipart
    @POST("reports")
    suspend fun createReport(
        @Header("Authorization") authHeader: String,
        @Part("title") title: RequestBody,
        @Part("violation") violation: RequestBody,
        @Part("location") location: RequestBody,
        @Part("date") date: RequestBody,
        @Part("actors") actors: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part("is_anonymous") isAnonymous: RequestBody,
        @Part evidence: MultipartBody.Part?
    ): Response<ReportResponse>

    @GET("reports/anonymous/{unique_code}")
    suspend fun trackReport(
        @Header("Authorization") authHeader: String,
        @Path("unique_code") uniqueCode: String
    ): Response<DetailReportResponse>

    @GET("reports/{unique_code}/chats/anonymous")
    suspend fun getAnonymousChat(
        @Header("Authorization") authHeader: String,
        @Path("unique_code") uniqueCode: String
    ): Response<ChatResponse>

    @POST("reports/{unique_code}/chats/anonymous")
    suspend fun sendAnonymousChat(
        @Header("Authorization") authHeader: String,
        @Path("unique_code") uniqueCode: String,
        @Body request: SendChatRequest
    ): Response<SendChatResponse>

    @GET("reports/{report_id}/chats")
    suspend fun getChat(
        @Header("Authorization") authHeader: String,
        @Path("report_id") reportId: Int
    ): Response<ChatResponse>

    @POST("reports/{report_id}/chats")
    suspend fun sendChat(
        @Header("Authorization") authHeader: String,
        @Path("report_id") reportId: Int,
        @Body request: SendChatRequest
    ): Response<SendChatResponse>
}