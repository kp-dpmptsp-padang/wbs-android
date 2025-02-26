package com.example.wbsdpmptsp.repository

import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.data.remote.response.NotificationResponse
import com.example.wbsdpmptsp.data.remote.retro.ApiService
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.flow.first

class NotificationRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun notification(): Result<NotificationResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val response = apiService.notification("Bearer $accessToken")

            if (response.isSuccessful) {
                response.body()?.let { notificationResponse ->
                    Result.Success(notificationResponse)
                } ?: Result.Error("Notification not found")
            } else {
                Result.Error(response.message() ?: "Notification not found")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun readAllNotification(): Result<MessageResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val response = apiService.readAllNotification("Bearer $accessToken")

            if (response.isSuccessful) {
                response.body()?.let { messageResponse ->
                    Result.Success(messageResponse)
                } ?: Result.Error("Notification not found")
            } else {
                Result.Error(response.message() ?: "Notification not found")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun readNotification(id: Int): Result<MessageResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val response = apiService.readNotification("Bearer $accessToken", id)

            if (response.isSuccessful) {
                response.body()?.let { messageResponse ->
                    Result.Success(messageResponse)
                } ?: Result.Error("Notification not found")
            } else {
                Result.Error(response.message() ?: "Notification not found")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    companion object {
        @Volatile
        private var instance: NotificationRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): NotificationRepository {
            return instance ?: synchronized(this) {
                instance ?: NotificationRepository(apiService, userPreference).also { instance = it }
            }
        }
    }
}