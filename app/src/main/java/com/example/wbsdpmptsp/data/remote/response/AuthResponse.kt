package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("data")
	val data: DataAuth? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataAuth(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("user")
	val user: UserAuth? = null
)

data class UserAuth(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
