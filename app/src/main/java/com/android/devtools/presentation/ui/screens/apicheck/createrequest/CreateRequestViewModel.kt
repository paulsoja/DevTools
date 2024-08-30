package com.android.devtools.presentation.ui.screens.apicheck.createrequest

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.devtools.domain.api.RequestParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateRequestViewModel @Inject constructor(

) : ViewModel() {

    private val _requestParamsState = MutableStateFlow(listOf(RequestParam.empty))
    val requestParamsState = _requestParamsState.asStateFlow()

    private val _urlState = MutableStateFlow("")
    val urlState = _urlState.asStateFlow()

    fun setUrl(url: String) {
        _urlState.updateAndGet { url }
    }

    fun addRequestParam(index: Int, params: RequestParam) = viewModelScope.launch {
        _requestParamsState.updateAndGet {
            it.toMutableList().apply { this[index] = it[index].copy(key = params.key, value = params.value) }
        }
    }

    fun sendRequest(url: String, params: List<RequestParam>) {
        Timber.d("sendRequest: url=$url, params=$params")
        viewModelScope.launch {

        }
    }
}