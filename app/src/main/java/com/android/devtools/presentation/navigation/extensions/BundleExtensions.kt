package com.android.devtools.presentation.navigation.extensions

import android.os.Build
import android.os.Bundle

@Suppress("DEPRECATION")
inline fun <reified T : Any> Bundle.parcelable(key: String?): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        get(key) as? T
    }
}