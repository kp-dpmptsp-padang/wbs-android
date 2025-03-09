package com.example.wbsdpmptsp.data.remote.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("current_password")
    val currentPassword: String,

    @SerializedName("new_password")
    val newPassword: String,

    @SerializedName("new_password_confirmation")
    val newPasswordConfirmation: String
)
