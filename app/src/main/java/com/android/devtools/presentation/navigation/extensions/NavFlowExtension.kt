package com.android.devtools.presentation.navigation.extensions

import androidx.navigation.NavBackStackEntry
import com.android.devtools.presentation.navigation.NavFlow
import com.android.devtools.presentation.navigation.NavFlowRoute

/**
 * Create a route with keys and values
 *
 * Can be used to pass params to the navigation
 */
fun NavFlow.routeWithParamsAndValues(
    vararg arg: Pair<String, Any?>?
): String {
    val params = arg.filterNotNull().joinToString("&") { "${it.first}=${it.second}" }
    return if (params.isNotEmpty()) {
        "${flowRoute.route}?$params"
    } else {
        flowRoute.route
    }
}

/**
 * Create a route only with keys
 *
 * Can be used as a navigation route.
 * Use not null keys that we have in [NavFlow]
 */
fun NavFlow.routeWithKeys(): NavFlowRoute {
    val params = listOfNotNull(
        argKey1?.let { "$it={$it}" },
        argKey2?.let { "$it={$it}" },
        argKey3?.let { "$it={$it}" }
    ).joinToString("&")

    val route = if (params.isNotEmpty()) {
        "${flowRoute.route}?$params"
    } else {
        flowRoute.route
    }

    return NavFlowRoute(route)
}

/**
 * Get argument from the [NavBackStackEntry] by the first key in [NavFlow]
 */
inline fun <reified T : Any> NavBackStackEntry.firstKeyValue(
    navFlow: NavFlow
): T? {
    return get(navFlow.argKey1)
}

/**
 * Get argument from the [NavBackStackEntry] by the first second in [NavFlow]
 */
inline fun <reified T : Any> NavBackStackEntry.secondKeyValue(
    navFlow: NavFlow
): T? {
    return get(navFlow.argKey2)
}

/**
 * Get argument from the [NavBackStackEntry] by the third key in [NavFlow]
 */
inline fun <reified T : Any> NavBackStackEntry.thirdKeyValue(
    navFlow: NavFlow
): T? {
    return get(navFlow.argKey3)
}

inline fun <reified T : Any> NavBackStackEntry.get(
    key: String?
): T? {
    return arguments?.parcelable(key)
}

fun NavFlow.toDeeplink(schema: String): String {
    val args = listOfNotNull(
        argKey1?.let { "$it={$it}" },
        argKey2?.let { "$it={$it}" },
        argKey3?.let { "$it={$it}" }
    )

    return if (args.isNotEmpty()) {
        "$schema://${this.label}?${args.joinToString("&")}"
    } else {
        "$schema://${this.label}"
    }
}
