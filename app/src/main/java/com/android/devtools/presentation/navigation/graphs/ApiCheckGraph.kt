package com.android.devtools.presentation.navigation.graphs

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.android.devtools.domain.api.RequestParam
import com.android.devtools.domain.api.toModel
import com.android.devtools.presentation.navigation.AppNavigator
import com.android.devtools.presentation.navigation.NavFlow
import com.android.devtools.presentation.navigation.extensions.firstKeyValue
import com.android.devtools.presentation.navigation.extensions.routeWithKeys
import com.android.devtools.presentation.navigation.navFlow.ApiCheckFlow
import com.android.devtools.presentation.ui.screens.apicheck.createrequest.CreateRequestScreen
import com.android.devtools.presentation.ui.screens.apicheck.requestlist.RequestListScreen
import com.android.devtools.presentation.ui.screens.apicheck.responseoverview.ResponseOverviewScreen

fun NavGraphBuilder.apiCheckGraph(route: NavFlow, appNavigator: AppNavigator, paddingValues: PaddingValues) =
    navigation(
        startDestination = ApiCheckFlow.CreateRequest.flowRoute.route,
        route = route.flowRoute.route
    ) {
        composable(
            route = ApiCheckFlow.RequestList.flowRoute.route
        ) {
            RequestListScreen(appNavigator, paddingValues)
        }

        composable(
            route = ApiCheckFlow.CreateRequest.flowRoute.route
        ) {
            CreateRequestScreen(appNavigator, paddingValues)
        }

        composable(
            route = ApiCheckFlow.ResponseOverview.routeWithKeys().route,
            arguments = listOf(
                navArgument(name = ApiCheckFlow.ResponseOverview.argKey1.orEmpty()) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arg = requireNotNull(backStackEntry.firstKeyValue<String>(ApiCheckFlow.ResponseOverview))
            val params = arg.toModel<List<RequestParam>>(emptyList())

            ResponseOverviewScreen(appNavigator, paddingValues, params)
        }
    }