package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChatResponse(

	@field:SerializedName("data")
	val data: DataChat? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemChat(

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: UserChat? = null
)

data class UserChat(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class DataChat(

	@field:SerializedName("data")
	val data: List<DataItemChat?>? = null
)
