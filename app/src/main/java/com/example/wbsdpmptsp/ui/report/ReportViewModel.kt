package com.example.wbsdpmptsp.ui.report

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.ReportResponse
import com.example.wbsdpmptsp.repository.ReportRepository
import com.example.wbsdpmptsp.utils.Result
import kotlinx.coroutines.launch

class ReportViewModel(private val repository: ReportRepository) : ViewModel() {

    private val _reportResult = MutableLiveData<Result<ReportResponse>>()
    val reportResult: LiveData<Result<ReportResponse>> = _reportResult

    fun createReport(
        context: Context,
        title: String,
        violation: String,
        location: String,
        date: String,
        actors: String,
        detail: String,
        isAnonymous: Boolean,
        fileUri: Uri?
    ) {
        _reportResult.value = Result.Loading
        viewModelScope.launch {
            val result = repository.createReport(
                context, title, violation, location, date, actors, detail, isAnonymous, fileUri
            )
            _reportResult.value = result
        }
    }

    fun resetReportResult() {
        _reportResult.value = Result.Empty
    }
}
