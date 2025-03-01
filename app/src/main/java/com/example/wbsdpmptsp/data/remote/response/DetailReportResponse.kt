package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailReportResponse(

	@field:SerializedName("data")
	val data: DataDetailReport? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Reporter(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class DataDetailReport(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("reporter")
	val reporter: Reporter? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("processor")
	val processor: ProcessorDetail? = null,

	@field:SerializedName("actors")
	val actors: String? = null,

	@field:SerializedName("admin_notes")
	val adminNotes: String? = null,

	@field:SerializedName("is_anonymous")
	val isAnonymous: Boolean? = null,

	@field:SerializedName("violation")
	val violation: String? = null,

	@field:SerializedName("rejection_reason")
	val rejectionReason: Any? = null,

	@field:SerializedName("files")
	val files: List<FilesItemDetail?>? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ProcessorDetail(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class FilesItemDetail(

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("file_type")
	val fileType: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
