package com.example.wbsdpmptsp.ui.track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.DetailReportResponse
import com.example.wbsdpmptsp.repository.TrackReportRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

class TrackReportViewModel(private val trackReportRepository: TrackReportRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _reportDetail = MutableLiveData<DetailReportResponse?>()
    val reportDetail: LiveData<DetailReportResponse?> = _reportDetail

    fun trackReport(uniqueCode: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                when (val result = trackReportRepository.trackAnonymousReport(uniqueCode)) {
                    is Result.Success -> {
                        _reportDetail.value = result.data
                    }
                    is Result.Error -> {
                        _errorMessage.value = "Data tidak ditemukan"
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

    fun clearReportDetail() {
        _reportDetail.value = null
        _errorMessage.value = null
        _isLoading.value = false
    }
}