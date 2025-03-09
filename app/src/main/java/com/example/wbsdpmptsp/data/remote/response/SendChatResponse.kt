package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class SendChatResponse(

	@field:SerializedName("data")
	val data: DataSendChat? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataSendChat(

	@field:SerializedName("data")
	val data: DataSendChat? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: UserSendChat? = null
)

data class UserSendChat(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
