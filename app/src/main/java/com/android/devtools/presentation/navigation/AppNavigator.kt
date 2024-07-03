package com.android.devtools.presentation.navigation

import androidx.annotation.MainThread
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.android.devtools.presentation.navigation.extensions.routeWithParamsAndValues

class AppNavigator(val navController: NavController) {

    @MainThread
    fun navigateToFlow(
        navigationFlow: NavFlow,
        navOptions: NavOptions? = null
    ) = with(navController) {
        val route = navigationFlow.flowRoute
        navigate(route.route, navOptions)

        /*navigate(
            route.route,
        ) {
            navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }*/
    }

    @MainThread
    fun navigateToFlowWithArgs(
        navigationFlow: NavFlow,
        navOptions: NavOptions? = null,
        vararg arg: Pair<String, Any>?
    ) = with(navController) {
        navigate(navigationFlow.routeWithParamsAndValues(*arg), navOptions)
    }

    fun back() {
        navController.navigateUp()
    }

    fun backWithResult(key: String, result: String) {
        navController.previousBackStackEntry?.savedStateHandle?.set(key, result)
        navController.navigateUp()
    }

    fun getResult(key: String): String? {
        return navController.currentBackStackEntry?.savedStateHandle?.remove<String>(key)
    }
}