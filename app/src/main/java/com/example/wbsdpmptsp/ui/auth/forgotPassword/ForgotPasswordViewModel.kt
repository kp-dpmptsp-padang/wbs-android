package com.example.wbsdpmptsp.ui.auth.forgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.repository.UserRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val repository: UserRepository) : ViewModel() {
    private val _forgotPasswordResult = MutableLiveData<Result<MessageResponse>>()
    val forgotPasswordResult: LiveData<Result<MessageResponse>> = _forgotPasswordResult

    private val _verifyCodeResult = MutableLiveData<Result<MessageResponse>>()
    val verifyCodeResult: LiveData<Result<MessageResponse>> = _verifyCodeResult

    private val _resetPasswordResult = MutableLiveData<Result<MessageResponse>>()
    val resetPasswordResult: LiveData<Result<MessageResponse>> = _resetPasswordResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun forgotPassword(email: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.forgotPassword(email)
            _forgotPasswordResult.value = result
            _isLoading.value = false
        }
    }

    fun verifyCode(email: String, code: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.verifyCode(email, code)
            _verifyCodeResult.value = result
            _isLoading.value = false
        }
    }

    fun resetPassword(email: String, code: String, password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = repository.resetPassword(email, code, password)
            _resetPasswordResult.value = result
            _isLoading.value = false
        }
    }
}