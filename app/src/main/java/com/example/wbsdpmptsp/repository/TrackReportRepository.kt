package com.example.wbsdpmptsp.repository

import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.response.DetailReportResponse
import com.example.wbsdpmptsp.data.remote.retro.ApiService
import kotlinx.coroutines.flow.first
import com.example.wbsdpmptsp.utils.Result
import retrofit2.HttpException

class TrackReportRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
){
    suspend fun trackAnonymousReport(uniqueCode: String): Result<DetailReportResponse> {
        return try {
            val token = userPreference.getAccessToken().first() ?: return Result.Error("Token not found")
            val authHeader = "Bearer $token"
            val response = apiService.trackReport(authHeader, uniqueCode)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Response body kosong")
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Result.Error(errorBody ?: "Terjadi kesalahan saat melacak laporan")
            }
        } catch (e: HttpException) {
            Result.Error("Error jaringan: ${e.message}")
        } catch (e: Exception) {
            Result.Error("Terjadi kesalahan: ${e.message}")
        }
    }

    companion object {
        @Volatile
        private var instance: TrackReportRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference): TrackReportRepository {
            return instance ?: synchronized(this) {
                instance ?: TrackReportRepository(apiService, userPreference).also { instance = it }
            }
        }
    }
}