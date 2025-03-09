package com.example.wbsdpmptsp.data.remote.request

import com.google.gson.annotations.SerializedName

data class SendChatRequest(
    @SerializedName("message")
    val message: String
)
