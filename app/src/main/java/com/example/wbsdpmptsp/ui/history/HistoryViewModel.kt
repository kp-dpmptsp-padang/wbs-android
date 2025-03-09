package com.example.wbsdpmptsp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.DataItem
import com.example.wbsdpmptsp.data.remote.response.DetailReportResponse
import com.example.wbsdpmptsp.repository.HistoryRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    private val _history = MutableLiveData<List<DataItem?>?>()
    val history: LiveData<List<DataItem?>?> = _history

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _currentStatus = MutableLiveData<String>()
    val currentStatus: LiveData<String> = _currentStatus

    private val _reportDetail = MutableLiveData<DetailReportResponse?>()
    val reportDetail: LiveData<DetailReportResponse?> = _reportDetail

    private val _detailLoading = MutableLiveData<Boolean>()
    val detailLoading: LiveData<Boolean> = _detailLoading

    private val _detailError = MutableLiveData<String>()
    val detailError: LiveData<String> = _detailError

    fun setCurrentStatus(status: String) {
        _currentStatus.value = status
    }

    fun getHistory(status: String) {
        _loading.value = true
        _error.value = ""
        _currentStatus.value = status

        viewModelScope.launch {
            when (val result = repository.history()) {
                is Result.Success -> {
                    val filteredData = result.data.data?.filter { it?.status == status }
                    _history.value = filteredData
                }
                is Result.Error -> {
                    _error.value = result.error
                }
                Result.Loading -> {
                    _loading.value = true
                }
                is Result.Empty -> {
                    _history.value = emptyList()
                }
            }
            _loading.value = false
        }
    }

    fun getDetailReport(id: Int) {
        _detailLoading.value = true
        _detailError.value = ""
        viewModelScope.launch {
            when (val result = repository.detailReport(id)) {
                is Result.Success -> {
                    _reportDetail.value = result.data
                }
                is Result.Error -> {
                    _detailError.value = result.error
                }
                Result.Loading -> {
                    _detailLoading.value = true
                }
                is Result.Empty -> {

                }
            }
            _detailLoading.value = false
        }
    }

    fun clearReportDetail() {
        _reportDetail.value = null
        _detailError.value = ""
    }
}