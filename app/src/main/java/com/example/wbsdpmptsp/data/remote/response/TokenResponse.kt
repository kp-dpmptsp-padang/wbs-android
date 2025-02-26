package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
	@SerializedName("refresh_token")
	val refreshToken: String
)

data class TokenResponse(

	@field:SerializedName("data")
	val data: DataToken? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataToken(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null
)
