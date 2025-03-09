package com.example.wbsdpmptsp.data.remote.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("name")
    val name: String,
)
