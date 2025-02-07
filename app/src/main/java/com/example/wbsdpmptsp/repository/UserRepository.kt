package com.example.wbsdpmptsp.repository

import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.request.LoginRequest
import com.example.wbsdpmptsp.data.remote.request.RegisterRequest
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import com.example.wbsdpmptsp.data.remote.response.ErrorResponse
import com.example.wbsdpmptsp.data.remote.retro.ApiService
import com.example.wbsdpmptsp.utils.Result
import com.google.gson.Gson
import java.io.IOException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
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