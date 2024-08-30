package com.android.devtools.domain.api

enum class RequestConfigType(val title: String) {
    QUERY("Query"),
    HEADERS("Headers"),
    AUTHORIZATION("Authorization"),
    BODY("Body")
}
