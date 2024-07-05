package com.android.devtools.presentation.navigation.handlenavigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.android.devtools.presentation.navigation.AppNavigator
import com.android.devtools.presentation.navigation.command.NavigationCommand
import com.android.devtools.presentation.navigation.util.NavigateBack
import com.android.devtools.presentation.navigation.util.NavigateToFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HandleNavigation(
    appNavigator: AppNavigator,
    navigationCommand: SharedFlow<NavigationCommand?>,
    back: @Composable (NavController) -> Unit = { NavigateBack(it) },
    navToOptions: NavOptions? = null,
) = HandleNavigation(
    navController = appNavigator.navController,
    navigationCommand = navigationCommand,
    back = back,
    navToOptions = navToOptions
)

@Composable
private fun HandleNavigation(
    navController: NavController,
    navigationCommand: SharedFlow<NavigationCommand?>,
    back: @Composable (NavController) -> Unit = { NavigateBack(it) },
    navToOptions: NavOptions? = null,
) {
    val navigation by navigationCommand.collectAsState(initial = null)

    when (val result = navigation) {
        is NavigationCommand.To -> NavigateToFlow(navController, result.direction.flowRoute.route, navToOptions)

        NavigationCommand.Back -> back(navController)

        null -> Unit
    }
}