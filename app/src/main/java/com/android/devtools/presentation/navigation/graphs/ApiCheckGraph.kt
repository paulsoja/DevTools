package com.android.devtools.presentation.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.android.devtools.presentation.navigation.AppNavigator
import com.android.devtools.presentation.navigation.NavFlow
import com.android.devtools.presentation.navigation.navFlow.ApiCheckFlow
import com.android.devtools.presentation.ui.screens.apicheck.ApiCheckScreen

fun NavGraphBuilder.apiCheckGraph(route: NavFlow, appNavigator: AppNavigator, paddingValues: PaddingValues) =
    navigation(
        startDestination = ApiCheckFlow.ApiCheck.flowRoute.route,
        route = route.flowRoute.route
    ) {
        composable(
            route = ApiCheckFlow.ApiCheck.flowRoute.route
        ) {
            ApiCheckScreen(appNavigator, paddingValues)
        }
    }