package com.example.wbsdpmptsp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.AuthResponse
import com.example.wbsdpmptsp.repository.UserRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<AuthResponse>>()
    val loginResult: LiveData<Result<AuthResponse>> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _loginResult.value = repository.login(email, password)
            _isLoading.value = false
        }
    }
}