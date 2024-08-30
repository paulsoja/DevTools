package com.android.devtools.presentation.ui.screens.apicheck.responseoverview

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.devtools.domain.api.RequestParam
import com.android.devtools.domain.usecase.SendRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ResponseOverviewViewModel @Inject constructor(
    private val sendRequestUseCase: SendRequestUseCase
) : ViewModel() {

    private val _resultTextState = MutableStateFlow<AnnotatedString>(AnnotatedString.Builder().toAnnotatedString())
    val resultTextState = _resultTextState.asStateFlow()

    fun trySendRequest(
        list: List<RequestParam>,
        url: String,
        requestType: String
    ) {
        viewModelScope.launch {
            val params = SendRequestUseCase.Params(list, url, requestType)

            sendRequestUseCase.invoke(params)
                .catch {
                    Timber.d("sendRequest: catch=$it")
                }
                .collectLatest { result ->
                    Timber.d("sendRequest: collect=$result")
                    _resultTextState.updateAndGet {
                        result
                    }
                }
        }
    }
}