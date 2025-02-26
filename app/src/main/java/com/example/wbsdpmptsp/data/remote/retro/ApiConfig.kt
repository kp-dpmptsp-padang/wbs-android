package com.example.wbsdpmptsp.data.remote.retro

import android.content.Context
import com.example.wbsdpmptsp.BuildConfig
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.utils.AuthInterceptor
import com.example.wbsdpmptsp.utils.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun createApiService(context: Context): ApiService {
        val tokenManager = TokenManager(context, UserPreference(context))

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

