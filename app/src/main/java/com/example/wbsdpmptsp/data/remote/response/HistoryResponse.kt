package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("processor")
	val processor: Any? = null,

	@field:SerializedName("actors")
	val actors: String? = null,

	@field:SerializedName("admin_notes")
	val adminNotes: Any? = null,

	@field:SerializedName("is_anonymous")
	val isAnonymous: Boolean? = null,

	@field:SerializedName("violation")
	val violation: String? = null,

	@field:SerializedName("rejection_reason")
	val rejectionReason: Any? = null,

	@field:SerializedName("files")
	val files: List<FilesItem?>? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("detail")
	val detail: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Processor(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class FilesItem(

	@field:SerializedName("file_path")
	val filePath: String? = null,

	@field:SerializedName("file_type")
	val fileType: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
