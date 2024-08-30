package com.android.devtools.domain.usecase

import androidx.compose.ui.text.AnnotatedString
import com.android.devtools.domain.api.RequestParam
import com.android.devtools.domain.repository.ApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendRequestUseCase @Inject constructor(
    private val apiRepository: ApiRepository,
) {

    data class Params(
        val list: List<RequestParam>,
        val url: String,
        val requestType: String
    )

    suspend fun invoke(param: Params): Flow<AnnotatedString> {
        return apiRepository.sendRequest(param.copy(list = param.list.filter { it.key.isNotEmpty() }))
    }
}