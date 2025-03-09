package com.example.wbsdpmptsp.ui.auth.changePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.MessageResponse
import com.example.wbsdpmptsp.repository.UserRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _updatePasswordResult = MutableLiveData<Result<MessageResponse>?>()
    val updatePasswordResult: LiveData<Result<MessageResponse>?> = _updatePasswordResult

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> = _passwordError

    fun updatePassword(currentPassword: String, newPassword: String, confirmPassword: String) {
        _passwordError.value = null

        when {
            currentPassword.isEmpty() -> {
                _passwordError.value = "Harap isi Kata Sandi saat ini"
                return
            }
            newPassword.isEmpty() -> {
                _passwordError.value = "Harap isi Kata Sandi baru"
                return
            }
            confirmPassword.isEmpty() -> {
                _passwordError.value = "Harap isi konfirmasi Kata Sandi"
                return
            }
            newPassword != confirmPassword -> {
                _passwordError.value = "Kata Sandi baru dan konfirmasi kata sandi tidak cocok"
                return
            }
            newPassword.length < 6 -> {
                _passwordError.value = "Kata Sandi minimal 6 karakter"
                return
            }
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.updatePassword(currentPassword, newPassword, confirmPassword)
                _updatePasswordResult.value = result
            } catch (e: Exception) {
                _updatePasswordResult.value = Result.Error(e.message ?: "Unknown error occurred")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetUpdatePasswordResult() {
        _updatePasswordResult.value = null
        _passwordError.value = null
    }
}