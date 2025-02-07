package com.example.wbsdpmptsp.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("password_confirmation")
    val confirmPassword: String,
)
