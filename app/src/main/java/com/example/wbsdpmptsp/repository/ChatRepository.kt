package com.example.wbsdpmptsp.repository

import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.response.ChatResponse
import com.example.wbsdpmptsp.data.remote.retro.ApiService
import kotlinx.coroutines.flow.first
import com.example.wbsdpmptsp.utils.Result


class ChatRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
) {
    suspend fun getAnonymousChat(uniqueCode: String): Result<ChatResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }
            val response = apiService.getAnonymousChat("Bearer $accessToken", uniqueCode)
            if (response.isSuccessful) {
                response.body()?.let { chatResponse ->
                    Result.Success(chatResponse)
                } ?: Result.Error("Chat not found")
            } else {
                Result.Error(response.message() ?: "Chat not found")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    companion object {
        @Volatile
        private var instance: ChatRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): ChatRepository {
            return instance ?: synchronized(this) {
                instance ?: ChatRepository(apiService, userPreference).also { instance = it }
            }
        }
    }
}