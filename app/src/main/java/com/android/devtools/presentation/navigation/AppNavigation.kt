package com.android.devtools.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.android.devtools.presentation.ui.theme.RippleCustomTheme

@Composable
fun AppNavigation(navController: NavHostController) {
    val appNavigator: AppNavigator = remember(navController) {
        AppNavigator(navController)
    }

    CompositionLocalProvider(LocalRippleTheme provides RippleCustomTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
            bottomBar = {

            },
        ) { paddingValues ->
            StartFlow(
                appNavigator = appNavigator,
                paddingValues = paddingValues,
                navController = navController,
                startDestination = ""
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
        startDestination = startDestination
    ) {

    }
}