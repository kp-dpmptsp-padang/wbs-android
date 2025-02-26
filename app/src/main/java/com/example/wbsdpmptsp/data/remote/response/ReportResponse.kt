package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ReportResponse(

	@field:SerializedName("data")
	val data: DataReport? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataReport(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("unique_code")
	val uniqueCode: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)
