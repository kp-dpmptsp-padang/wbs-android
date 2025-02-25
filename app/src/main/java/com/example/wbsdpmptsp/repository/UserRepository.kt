package com.example.wbsdpmptsp.repository

import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.request.LoginRequest
import com.example.wbsdpmptsp.data.remote.request.LogoutRequest
import com.example.wbsdpmptsp.data.remote.request.RegisterRequest
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import com.example.wbsdpmptsp.data.remote.response.ErrorResponse
import com.example.wbsdpmptsp.data.remote.response.HistoryResponse
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.data.remote.response.ProfileResponse
import com.example.wbsdpmptsp.data.remote.retro.ApiService
import com.example.wbsdpmptsp.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.IOException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getRefreshToken(): Flow<String?> = userPreference.getRefreshToken()

    suspend fun register(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<AuthResponse> {
        return try {
            val request = RegisterRequest(name, email, password, confirmPassword)
            val response = apiService.register(request)

            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    userPreference.saveUser(authResponse)
                    Result.Success(authResponse)
                } ?: Result.Error("Registration failed")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

                when {
                    errorResponse?.message == "Password confirmation does not match" -> {
                        Result.Error(errorResponse.message)
                    }
                    errorResponse?.errors?.isNotEmpty() == true -> {
                        val errorMessage = errorResponse.errors.firstOrNull()?.message
                        Result.Error(errorMessage ?: "Validation failed")
                    }
                    else -> {
                        Result.Error(errorResponse?.message ?: "Registration failed")
                    }
                }
            }
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.Error("Network error. Please check your connection.")
                else -> Result.Error("An unexpected error occurred. Please try again.")
            }
        }
    }

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val request = LoginRequest(email, password)
            val response = apiService.login(request)

            if (response.isSuccessful) {
                response.body()?.let { authResponse ->
                    userPreference.saveUser(authResponse)
                    Result.Success(authResponse)
                } ?: Result.Error("Login failed")
            } else {
                Result.Error(response.message() ?: "Login failed")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun logout(refreshToken: String): Result<MessageResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()
            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val request = LogoutRequest(refreshToken)
            val response = apiService.logout("Bearer $accessToken", request)

            if (response.isSuccessful) {
                response.body()?.let { messageResponse ->
                    userPreference.clearUser()
                    Result.Success(messageResponse)
                } ?: Result.Error("Logout failed")
            } else {
                Result.Error(response.message() ?: "Logout failed")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun profile(): Result<ProfileResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val response = apiService.profile("Bearer $accessToken")

            if (response.isSuccessful) {
                response.body()?.let { profileResponse ->
                    Result.Success(profileResponse)
                } ?: Result.Error("Profile not found")
            } else {
                Result.Error(response.message() ?: "Profile not found")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun history(): Result<HistoryResponse>{
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val response = apiService.history("Bearer $accessToken")

            if (response.isSuccessful) {
                response.body()?.let { historyResponse ->
                    Result.Success(historyResponse)
                } ?: Result.Error("History not found")
            } else {
                Result.Error(response.message() ?: "History not found")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}