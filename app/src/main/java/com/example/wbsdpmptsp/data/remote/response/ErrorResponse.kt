package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("errors")
    val errors: List<ValidationError>? = null
)

data class ValidationError(
    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("value")
    val value: String? = null,

    @field:SerializedName("msg")
    val message: String? = null,

    @field:SerializedName("path")
    val path: String? = null,

    @field:SerializedName("location")
    val location: String? = null
)