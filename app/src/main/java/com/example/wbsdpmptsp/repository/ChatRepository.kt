package com.example.wbsdpmptsp.repository

import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.request.SendChatRequest
import com.example.wbsdpmptsp.data.remote.response.ChatResponse
import com.example.wbsdpmptsp.data.remote.response.SendChatResponse
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

    suspend fun sendAnonymousChat(uniqueCode: String, message: String): Result<SendChatResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val messageRequest = SendChatRequest(message)

            val response = apiService.sendAnonymousChat("Bearer $accessToken", uniqueCode, messageRequest)
            if (response.isSuccessful) {
                response.body()?.let { sendChatResponse ->
                    Result.Success(sendChatResponse)
                } ?: Result.Error("Chat not send")
            } else {
                Result.Error(response.message() ?: "Chat not send")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    suspend fun getChat(reportId: Int): Result<ChatResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }
            val response = apiService.getChat("Bearer $accessToken", reportId)
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

    suspend fun sendChat(reportId: Int, message: String): Result<SendChatResponse> {
        return try {
            val accessToken = userPreference.getAccessToken().first()

            if (accessToken == null) {
                return Result.Error("Access token not found")
            }

            val messageRequest = SendChatRequest(message)

            val response = apiService.sendChat("Bearer $accessToken", reportId, messageRequest)
            if (response.isSuccessful) {
                response.body()?.let { sendChatResponse ->
                    Result.Success(sendChatResponse)
                } ?: Result.Error("Chat not send")
            } else {
                Result.Error(response.message() ?: "Chat not send")
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