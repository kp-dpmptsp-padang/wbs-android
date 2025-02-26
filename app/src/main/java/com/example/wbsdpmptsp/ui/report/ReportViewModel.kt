package com.example.wbsdpmptsp.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.request.ReportRequest
import com.example.wbsdpmptsp.data.remote.response.ReportResponse
import com.example.wbsdpmptsp.repository.ReportRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

class ReportViewModel(private val repository: ReportRepository) : ViewModel() {

    private val _reportResult = MutableLiveData<Result<ReportResponse>>()
    val reportResult: LiveData<Result<ReportResponse>> = _reportResult

    fun createReport(request: ReportRequest) {
        _reportResult.value = Result.Loading
        viewModelScope.launch {
            val result = repository.createReport(request)
            _reportResult.value = result
        }
    }
}
