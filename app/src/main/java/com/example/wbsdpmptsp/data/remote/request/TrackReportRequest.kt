package com.example.wbsdpmptsp.data.remote.request

import com.google.gson.annotations.SerializedName

data class TrackReportRequest(
    @SerializedName("unique_code")
    val uniqueCode: String? = null
)
