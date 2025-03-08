package com.example.wbsdpmptsp.di

import android.content.Context
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.retro.ApiConfig
import com.example.wbsdpmptsp.repository.HistoryRepository
import com.example.wbsdpmptsp.repository.NotificationRepository
import com.example.wbsdpmptsp.repository.ReportRepository
import com.example.wbsdpmptsp.repository.TrackReportRepository
import com.example.wbsdpmptsp.repository.UserRepository
import com.example.wbsdpmptsp.utils.TokenManager

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.createApiService(context)
        val userPreference = UserPreference(context)
        return UserRepository.getInstance(apiService, userPreference)
    }

    fun provideHistoryRepository(context: Context): HistoryRepository {
        val apiService = ApiConfig.createApiService(context)
        val userPreference = UserPreference(context)
        return HistoryRepository.getInstance(apiService, userPreference)
    }

    fun provideNotificationRepository(context: Context): NotificationRepository {
        val apiService = ApiConfig.createApiService(context)
        val userPreference = UserPreference(context)
        return NotificationRepository.getInstance(apiService, userPreference)
    }

    fun provideReportRepository(context: Context): ReportRepository {
        val apiService = ApiConfig.createApiService(context)
        val userPreference = UserPreference(context)
        val tokenManager = TokenManager(context, userPreference)
        return ReportRepository.getInstance(apiService, userPreference, tokenManager)
    }

    fun provideTrackReportRepository(context: Context): TrackReportRepository {
        val apiService = ApiConfig.createApiService(context)
        val userPreference = UserPreference(context)
        val tokenManager = TokenManager(context, userPreference)
        return TrackReportRepository.getInstance(apiService, userPreference)
    }
}