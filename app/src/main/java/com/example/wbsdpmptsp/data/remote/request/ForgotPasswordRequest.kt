package com.example.wbsdpmptsp.data.remote.request

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("email")
    val email: String,
)
