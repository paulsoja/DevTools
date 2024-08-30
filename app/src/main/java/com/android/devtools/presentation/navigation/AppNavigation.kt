package com.android.devtools.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.android.devtools.presentation.navigation.graphs.apiCheckGraph
import com.android.devtools.presentation.navigation.menu.MenuNavItemsFlow
import com.android.devtools.presentation.navigation.navFlow.ApiCheckFlow
import com.android.devtools.presentation.ui.theme.RippleCustomTheme
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(navController: NavHostController) {
    val appNavigator: AppNavigator = remember(navController) {
        AppNavigator(navController)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {
        var visibleMenu by rememberSaveable {
            mutableStateOf(false)
        }

        LaunchedEffect(key1 = navController.currentBackStackEntryFlow) {
            navController.currentBackStackEntryFlow.collectLatest {
                visibleMenu = when (it.destination.route?.substringBefore("?")) {
                    ApiCheckFlow.ResponseOverview.flowRoute.route -> false
                    else -> true
                }
            }
        }

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            topBar = {
                AnimatedVisibility(visible = visibleMenu) {
                    TopAppBar(
                        title = { Text(text = "DevTools") },
                        scrollBehavior = scrollBehavior,
                    )
                }
            },
            bottomBar = {
                BottomNavBar(showBottomBar = visibleMenu, appNavigator = appNavigator)
            },
        ) { paddingValues ->
            StartFlow(
                appNavigator = appNavigator,
                paddingValues = paddingValues,
                navController = navController,
                startDestination = MenuNavItemsFlow.ApiCheck.flowRoute.route
            )
        }
    }
}

@Composable
private fun StartFlow(
    appNavigator: AppNavigator,
    paddingValues: PaddingValues,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        apiCheckGraph(
            route = MenuNavItemsFlow.ApiCheck,
            appNavigator = appNavigator,
            paddingValues = paddingValues
        )
    }
}

@Composable
private fun BottomNavBar(
    showBottomBar: Boolean,
    appNavigator: AppNavigator,
) {
    AnimatedVisibility(
        visible = showBottomBar,
        enter = fadeIn(tween(600)),
        exit = fadeOut(tween(300)),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 2.dp)
                    .background(Color(0xFF212121))
                    .zIndex(8f)
            ) {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = { /* do something */ }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                        }
                        IconButton(onClick = { /* do something */ }) {
                            Icon(Icons.Filled.Check, contentDescription = "Localized description")
                        }
                        IconButton(onClick = { /* do something */ }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Localized description")
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { /* do something */ },
                            containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(Icons.Filled.Add, "Localized description")
                        }
                    }
                )
            }
        }
    )
}

fun NavBackStackEntry.getFormattedRoute(scheme: String): String {
    var formattedRoute = this.destination.route.orEmpty()
    this.arguments?.let {
        for (key in it.keySet()) {
            val value = it.get(key)?.toString().orEmpty()
            formattedRoute = formattedRoute.replace("{$key}", value)
        }
    }
    return "$scheme://$formattedRoute"
}