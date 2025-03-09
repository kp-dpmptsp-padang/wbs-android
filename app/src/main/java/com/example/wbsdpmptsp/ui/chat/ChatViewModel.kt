package com.example.wbsdpmptsp.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.DataItemChat
import com.example.wbsdpmptsp.repository.ChatRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

class ChatViewModel (private val repository: ChatRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: MutableLiveData<String?> = _errorMessage

    private val _chat = MutableLiveData<List<DataItemChat?>?>()
    val chat: MutableLiveData<List<DataItemChat?>?> = _chat

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
}