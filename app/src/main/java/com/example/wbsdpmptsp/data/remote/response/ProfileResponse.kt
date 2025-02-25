package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: DataProfile? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataProfile(

	@field:SerializedName("user")
	val user: UserProfile? = null
)

data class UserProfile(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("reportCount")
	val reportCount: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
