package com.example.wbsdpmptsp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wbsdpmptsp.data.remote.response.DataItem
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

    fun getHistory(status: String) {
        _loading.value = true
        viewModelScope.launch {
            when (val result = repository.history()) {
                is Result.Success -> {
                    _history.value = result.data.data?.filter { it?.status == status }
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
}