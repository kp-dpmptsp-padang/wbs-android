package com.example.wbsdpmptsp.repository

import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.request.ReportRequest
import com.example.wbsdpmptsp.data.remote.response.ReportResponse
import com.example.wbsdpmptsp.data.remote.retro.ApiService
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.flow.first

class ReportRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun createReport(request: ReportRequest): Result<ReportResponse> {
        return try {
            val response = apiService.createReport("Bearer ${userPreference.getAccessToken().first().toString()}", request)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception(response.message()).toString())
            }
        } catch (e: Exception) {
            Result.Error(e.toString())
        }
    }

    companion object {
        @Volatile
        private var instance: ReportRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): ReportRepository {
            return instance ?: synchronized(this) {
                instance ?: ReportRepository(apiService, userPreference).also { instance = it }
            }
        }
    }
}