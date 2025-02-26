package com.example.wbsdpmptsp.data.remote.response

import com.google.gson.annotations.SerializedName

data class NotificationResponse(

	@field:SerializedName("data")
	val data: DataNotif? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Meta(

	@field:SerializedName("unread_count")
	val unreadCount: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
)

data class DataItemNotif(

	@field:SerializedName("is_read")
	val isRead: Boolean? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DataNotif(

	@field:SerializedName("data")
	val data: List<DataItemNotif?>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)
