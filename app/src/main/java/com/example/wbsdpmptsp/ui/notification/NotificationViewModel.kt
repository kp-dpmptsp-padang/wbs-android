package com.example.wbsdpmptsp.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.DataItemNotif
import com.example.wbsdpmptsp.repository.NotificationRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

class NotificationViewModel(private val repository: NotificationRepository) : ViewModel() {
    private val _notification = MutableLiveData<List<DataItemNotif?>?>()
    val notification: MutableLiveData<List<DataItemNotif?>?> = _notification

    private val _loading = MutableLiveData<Boolean>()
    val loading: MutableLiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: MutableLiveData<String> = _error

    fun getNotification() {
        _loading.value = true
        _error.value = ""
        viewModelScope.launch {
            when (val result = repository.notification()) {
                is Result.Success -> {
                    _notification.value = result.data.data?.data
                }
                is Result.Error -> {
                    _error.value = result.error
                }
                Result.Loading -> {
                    _loading.value = true
                }
            }
            _loading.value = false
        }
    }

    fun readAllNotification() {
        viewModelScope.launch {
            when (val result = repository.readAllNotification()) {
                is Result.Success -> {
                    getNotification()
                }
                is Result.Error -> {
                    _error.value = result.error
                }
                Result.Loading -> {
                    _loading.value = true
                }
            }
        }
    }

    fun readNotification(id: Int) {
        viewModelScope.launch {
            when (val result = repository.readNotification(id)) {
                is Result.Success -> {
                    getNotification()
                }
                is Result.Error -> {
                    _error.value = result.error
                }
                Result.Loading -> {
                    _loading.value = true
                }
            }
        }
    }
}