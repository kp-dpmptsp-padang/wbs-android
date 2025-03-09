package com.example.wbsdpmptsp.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.local.UserPreference
import com.example.wbsdpmptsp.data.remote.response.DataItemChat
import com.example.wbsdpmptsp.data.remote.response.DataSendChat
import com.example.wbsdpmptsp.data.remote.response.UserChat
import com.example.wbsdpmptsp.data.remote.response.UserSendChat
import com.example.wbsdpmptsp.repository.ChatRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ChatViewModel (
    private val repository: ChatRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    private fun getCurrentTimestamp(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(Date())
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> = _errorMessage

    private val _chat = MutableLiveData<List<DataItemChat?>?>()
    val chat: MutableLiveData<List<DataItemChat?>?> = _chat

    private val _sendChat = MutableLiveData<List<DataSendChat?>?>()
    val sendChat: MutableLiveData<List<DataSendChat?>?> = _sendChat

    fun getAnonymChat(uniqueCode: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                when (val result = repository.getAnonymousChat(uniqueCode)) {
                    is Result.Success -> {
                        _chat.value = result.data.data?.data

                        if (_chat.value.isNullOrEmpty()) {
                            _errorMessage.value = "Belum ada pesan chat"
                        }
                    }
                    is Result.Error -> {
                        _errorMessage.value = result.error
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                    is Result.Empty -> {
                        _errorMessage.value = "Data tidak ditemukan"
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendAnonymChat(uniqueCode: String, message: String) {
        val temporaryChat = DataItemChat(
            id = System.currentTimeMillis().toInt(),
            message = message,
            createdAt = getCurrentTimestamp(),
            user = null
        )

        val currentChatList = _chat.value?.toMutableList() ?: mutableListOf()
        currentChatList.add(temporaryChat)
        _chat.value = currentChatList

        viewModelScope.launch {
            try {
                _errorMessage.value = null

                when (val result = repository.sendAnonymousChat(uniqueCode, message)) {
                    is Result.Success -> {

                        val responseData = result.data.data?.data
                        if (responseData != null) {
                            val updatedList = _chat.value?.toMutableList()
                            val index = updatedList?.indexOf(temporaryChat) ?: -1
                            if (index != -1) {
                                val actualChat = DataItemChat(
                                    id = responseData.id ?: temporaryChat.id,
                                    message = responseData.message ?: message,
                                    createdAt = responseData.createdAt ?: temporaryChat.createdAt,
                                    user = responseData.user?.let { user ->
                                        UserChat(
                                            id = user.id,
                                            name = user.name,
                                            role = user.role
                                        )
                                    }
                                )
                                updatedList?.set(index, actualChat)
                                _chat.value = updatedList
                            }
                        }
                    }
                    is Result.Error -> {
                        _errorMessage.value = result.error
                        val updatedChatList = _chat.value?.toMutableList()
                        updatedChatList?.remove(temporaryChat)
                        _chat.value = updatedChatList
                    }
                    is Result.Loading -> {
                    }
                    is Result.Empty -> {
                        _errorMessage.value = "Data tidak ditemukan"
                        val updatedChatList = _chat.value?.toMutableList()
                        updatedChatList?.remove(temporaryChat)
                        _chat.value = updatedChatList
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
                val updatedChatList = _chat.value?.toMutableList()
                updatedChatList?.remove(temporaryChat)
                _chat.value = updatedChatList
            } finally {
            }
        }
    }

    fun getChat(reportId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                when (val result = repository.getChat(reportId)) {
                    is Result.Success -> {
                        _chat.value = result.data.data?.data

                        if (_chat.value.isNullOrEmpty()) {
                            _errorMessage.value = "Belum ada pesan chat"
                        }
                    }
                    is Result.Error -> {
                        _errorMessage.value = result.error
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                    is Result.Empty -> {
                        _errorMessage.value = "Data tidak ditemukan"
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendChat(reportId: Int, message: String) {
        viewModelScope.launch {
            val currentUser = getCurrentUser()
            val temporaryChat = DataItemChat(
                id = System.currentTimeMillis().toInt(),
                message = message,
                createdAt = getCurrentTimestamp(),
                user = currentUser
            )

            val currentChatList = _chat.value?.toMutableList() ?: mutableListOf()
            currentChatList.add(temporaryChat)
            _chat.value = currentChatList

            try {
                _errorMessage.value = null

                when (val result = repository.sendChat(reportId, message)) {
                    is Result.Success -> {

                        val responseData = result.data.data?.data
                        if (responseData != null) {
                            val updatedList = _chat.value?.toMutableList()
                            val index = updatedList?.indexOf(temporaryChat) ?: -1
                            if (index != -1) {
                                val actualChat = DataItemChat(
                                    id = responseData.id ?: temporaryChat.id,
                                    message = responseData.message ?: message,
                                    createdAt = responseData.createdAt ?: temporaryChat.createdAt,
                                    user = responseData.user?.let { user ->
                                        UserChat(
                                            id = user.id,
                                            name = user.name,
                                            role = user.role
                                        )
                                    }
                                )
                                updatedList?.set(index, actualChat)
                                _chat.value = updatedList
                            }
                        }
                    }
                    is Result.Error -> {
                        _errorMessage.value = result.error
                        val updatedChatList = _chat.value?.toMutableList()
                        updatedChatList?.remove(temporaryChat)
                        _chat.value = updatedChatList
                    }
                    is Result.Loading -> {
                    }
                    is Result.Empty -> {
                        _errorMessage.value = "Data tidak ditemukan"
                        val updatedChatList = _chat.value?.toMutableList()
                        updatedChatList?.remove(temporaryChat)
                        _chat.value = updatedChatList
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
                val updatedChatList = _chat.value?.toMutableList()
                updatedChatList?.remove(temporaryChat)
                _chat.value = updatedChatList
            } finally {
            }
        }
    }

    private suspend fun getCurrentUser(): UserChat? {
        val userId = userPreference.getUserId() ?: return null
        val userName = userPreference.getUserName() ?: ""
        val userRole = userPreference.getUserRole() ?: ""

        return UserChat(
            id = userId,
            name = userName,
            role = userRole
        )
    }
}