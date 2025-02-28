package com.example.wbsdpmptsp.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.data.remote.response.ProfileResponse
import com.example.wbsdpmptsp.repository.UserRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    private val _profileResult = MutableLiveData<Result<ProfileResponse>>()
    val profileResult: LiveData<Result<ProfileResponse>> = _profileResult

    private val _logoutResult = MutableLiveData<Result<MessageResponse>>()
    val logoutResult: LiveData<Result<MessageResponse>> = _logoutResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchProfile()
    }

     fun fetchProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _profileResult.value = repository.profile()
            } catch (e: Exception) {
                _profileResult.value = Result.Error(e.message ?: "Unknown error")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val refreshToken = repository.getRefreshToken().first()
                if (refreshToken != null) {
                    _logoutResult.value = repository.logout(refreshToken)
                } else {
                    _logoutResult.value = Result.Error("Refresh token not found")
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}