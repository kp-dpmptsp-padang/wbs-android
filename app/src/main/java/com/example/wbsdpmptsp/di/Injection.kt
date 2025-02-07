package com.example.wbsdpmptsp.di

import android.content.Context
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.retro.ApiConfig
import com.example.wbsdpmptsp.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.api
        val userPreference = UserPreference(context)
        return UserRepository.getInstance(apiService, userPreference)
    }
}