package com.example.wbsdpmptsp.data.remote.request

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("code")
    val code: String,

    @SerializedName("new_password")
    val password: String
)
