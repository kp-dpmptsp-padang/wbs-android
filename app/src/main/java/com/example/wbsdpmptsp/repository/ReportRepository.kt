package com.example.wbsdpmptsp.repository

import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.retro.ApiService

class ReportRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

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