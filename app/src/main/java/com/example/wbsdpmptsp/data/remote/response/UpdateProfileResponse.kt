package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("data")
	val data: DataUpdateProfile? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class UserUpdateProfile(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class DataUpdateProfile(

	@field:SerializedName("user")
	val user: UserUpdateProfile? = null
)
