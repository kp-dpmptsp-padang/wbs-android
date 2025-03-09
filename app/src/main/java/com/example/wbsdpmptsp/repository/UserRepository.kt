package com.example.wbsdpmptsp.repository

import android.util.Log
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.request.ChangePasswordRequest
import com.example.wbsdpmptsp.data.remote.request.ForgotPasswordRequest
import com.example.wbsdpmptsp.data.remote.request.LoginRequest
import com.example.wbsdpmptsp.data.remote.request.LogoutRequest
import com.example.wbsdpmptsp.data.remote.request.RegisterRequest
import com.example.wbsdpmptsp.data.remote.request.ResetPasswordRequest
import com.example.wbsdpmptsp.data.remote.request.UpdateProfileRequest
import com.example.wbsdpmptsp.data.remote.request.VerifyCodeRequest
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import com.example.wbsdpmptsp.data.remote.response.ErrorResponse
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.data.remote.response.ProfileResponse
import com.example.wbsdpmptsp.data.remote.response.UpdateProfileResponse
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

    suspend fun forgotPassword(email: String): Result<MessageResponse> {
        return try {
            val request = ForgotPasswordRequest(email)
            val response = apiService.forgotPassword(request)

            if (response.isSuccessful) {
                response.body()?.let { messageResponse ->
                    Result.Success(messageResponse)
                } ?: Result.Error("Forgot password failed")
            } else {
                Result.Error(response.message() ?: "Forgot password failed")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun verifyCode(email: String, code: String): Result<MessageResponse> {
        return try {
            val request = VerifyCodeRequest(email, code)
            val response = apiService.verifyCode(request)

            if (response.isSuccessful) {
                response.body()?.let { messageResponse ->
                    Result.Success(messageResponse)
                } ?: Result.Error("Verification failed")
            } else {
                Result.Error(response.message() ?: "Verification failed")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun resetPassword(email: String, code: String, password: String): Result<MessageResponse> {
        return try {
            val request = ResetPasswordRequest(email, code, password)
            val response = apiService.resetPassword(request)

            if (response.isSuccessful) {
                response.body()?.let { messageResponse ->
                    Result.Success(messageResponse)
                } ?: Result.Error("Password reset failed")
            } else {
                Result.Error(response.message() ?: "Password reset failed")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun updateProfile(name: String): Result<UpdateProfileResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val request = UpdateProfileRequest(name)
            val response = apiService.updateProfile("Bearer $accessToken", request)

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

    suspend fun updatePassword(oldPassword: String, newPassword: String, confirmPassword: String): Result<MessageResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val request = ChangePasswordRequest(oldPassword, newPassword, confirmPassword)
            val response = apiService.updatePassword("Bearer $accessToken", request)

            if (response.isSuccessful) {
                response.body()?.let { messageResponse ->
                    Result.Success(messageResponse)
                } ?: Result.Error("Password not updated")
            } else {
                Result.Error(response.message() ?: "Password not updated")
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