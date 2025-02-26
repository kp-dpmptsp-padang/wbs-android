package com.example.wbsdpmptsp.repository

import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.response.HistoryResponse
import com.example.wbsdpmptsp.data.remote.retro.ApiService
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.flow.first

class HistoryRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun history(): Result<HistoryResponse> {
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
        private var instance: HistoryRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): HistoryRepository {
            return instance ?: synchronized(this) {
                instance ?: HistoryRepository(apiService, userPreference).also { instance = it }
            }
        }
    }
}