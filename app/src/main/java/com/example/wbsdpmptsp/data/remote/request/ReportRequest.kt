package com.example.wbsdpmptsp.data.remote.request

import com.google.gson.annotations.SerializedName

data class ReportRequest(
    @SerializedName("title")
    val title: String,

    @SerializedName("violation")
    val violation: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("actors")
    val actors: String,

    @SerializedName("detail")
    val detail: String,

    @SerializedName("is_anonymous")
    val isAnonymous: Boolean,

    @SerializedName("evidence")
    val evidence: String? = null
)

