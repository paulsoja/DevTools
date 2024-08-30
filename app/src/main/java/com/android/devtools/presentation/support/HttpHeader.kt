package com.android.devtools.presentation.support

import kotlinx.serialization.Serializable

@Serializable
data class HttpHeader(
    val name: String,
    val value: String,
)
