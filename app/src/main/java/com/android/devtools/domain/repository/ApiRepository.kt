package com.android.devtools.domain.repository

import androidx.compose.ui.text.AnnotatedString
import com.android.devtools.domain.usecase.SendRequestUseCase
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    suspend fun sendRequest(param: SendRequestUseCase.Params): Flow<AnnotatedString>
}