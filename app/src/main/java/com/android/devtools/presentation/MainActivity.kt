package com.android.devtools.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.devtools.presentation.navigation.AppNavigation
import com.android.devtools.presentation.ui.theme.DevToolsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navHostController = rememberNavController()
            navigationComponent(navHostController)
        }
    }
}

fun ComponentActivity.navigationComponent(navHostController: NavHostController) = setContent {
    DevToolsTheme {
        AppNavigation(navHostController)
    }
}