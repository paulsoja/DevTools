package com.android.devtools.domain.api.config

data class RequestOverview(
    val code: Int,
    val method: String,
    val url: String,
    val baseUrl: String,
    val time: String,
    val duration: String,
    val size: String
)
