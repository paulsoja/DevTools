package com.android.devtools.presentation.navigation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.android.devtools.presentation.navigation.NavFlow

/**
 * Add the [Composable] to the [NavGraphBuilder]
 *
 * @param flow route for the destination
 * @param arguments list of arguments to associate with destination
 * @param deepLinks list of deep links to associate with the destinations
 * @param content composable for the destination
 */
fun NavGraphBuilder.composable(
    flow: NavFlow,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(flow, arguments, content)
}

/**
 * @param startDestination the starting destination's route for this NavGraph
 * @param route the destination's unique route
 * @param arguments list of arguments to associate with destination
 * @param deepLinks list of deep links to associate with the destinations
 * @param builder the builder used to construct the graph
 */
fun NavGraphBuilder.navigation(
    startDestination: NavFlow,
    route: NavFlow,
    arguments: List<NamedNavArgument> = emptyList(),
    builder: NavGraphBuilder.() -> Unit
) {
    navigation(
        startDestination,
        route,
        arguments,
        builder
    )
}