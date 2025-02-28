package com.example.wbsdpmptsp.repository

import android.content.Context
import android.net.Uri
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.response.ReportResponse
import com.example.wbsdpmptsp.data.remote.retro.ApiService
import com.example.wbsdpmptsp.ui.report.getFileName
import com.example.wbsdpmptsp.utils.Result
import com.example.wbsdpmptsp.utils.TokenManager
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ReportRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val tokenManager: TokenManager
) {

    suspend fun createReport(
        context: Context,
        title: String,
        violation: String,
        location: String,
        date: String,
        actors: String,
        detail: String,
        isAnonymous: Boolean,
        fileUri: Uri?
    ): Result<ReportResponse> {
        return try {
            var authToken = userPreference.getAccessToken().first() ?: return Result.Error("Token not found")

            val titleBody = RequestBody.create("text/plain".toMediaTypeOrNull(), title)
            val violationBody = RequestBody.create("text/plain".toMediaTypeOrNull(), violation)
            val locationBody = RequestBody.create("text/plain".toMediaTypeOrNull(), location)
            val dateBody = RequestBody.create("text/plain".toMediaTypeOrNull(), date)
            val actorsBody = RequestBody.create("text/plain".toMediaTypeOrNull(), actors)
            val detailBody = RequestBody.create("text/plain".toMediaTypeOrNull(), detail)
            val isAnonymousBody = RequestBody.create("text/plain".toMediaTypeOrNull(), isAnonymous.toString())

            val filePart = uriToMultipartBody(context, fileUri, "evidence_files")

            val response = apiService.createReport(
                "Bearer $authToken",
                titleBody, violationBody, locationBody, dateBody,
                actorsBody, detailBody, isAnonymousBody, filePart
            )

            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                if (response.code() == 401) {
                    val newToken = tokenManager.refreshToken()
                    if (newToken != null) {
                        tokenManager.saveAccessToken(newToken.data?.accessToken.toString())
                        authToken = userPreference.getAccessToken().first() ?: return Result.Error("Token not found")

                        val retryResponse = apiService.createReport(
                            "Bearer $authToken",
                            titleBody, violationBody, locationBody, dateBody,
                            actorsBody, detailBody, isAnonymousBody, filePart
                        )

                        if (retryResponse.isSuccessful) {
                            Result.Success(retryResponse.body()!!)
                        } else {
                            Result.Error(retryResponse.errorBody()?.string() ?: "Unknown error")
                        }
                    } else {
                        Result.Error("Failed to refresh token")
                    }
                } else {
                    Result.Error(response.errorBody()?.string() ?: "Unknown error")
                }
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred")
        }
    }

    private fun uriToMultipartBody(context: Context, uri: Uri?, paramName: String): MultipartBody.Part? {
        if (uri == null) return null

        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)

        val fileName = getFileName(context, uri) ?: "unknown_file_${System.currentTimeMillis()}"
        val tempFile = File(context.cacheDir, fileName)

        inputStream?.use { input ->
            FileOutputStream(tempFile).use { output ->
                input.copyTo(output)
            }
        }

        val mimeType = contentResolver.getType(uri) ?: "application/octet-stream"
        val requestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), tempFile)

        return MultipartBody.Part.createFormData(paramName, fileName, requestBody)
    }

    companion object {
        @Volatile
        private var instance: ReportRepository? = null

        fun getInstance(apiService: ApiService, userPreference: UserPreference, tokenManager: TokenManager): ReportRepository {
            return instance ?: synchronized(this) {
                instance ?: ReportRepository(apiService, userPreference, tokenManager).also { instance = it }
            }
        }
    }
}