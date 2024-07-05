package com.android.devtools.presentation.navigation.command

import androidx.navigation.NavOptionsBuilder
import com.android.devtools.presentation.navigation.NavFlow

sealed class NavigationCommand {
    /**
     * @param direction to which navigation should be run
     */
    data class To(
        val direction: NavFlow,
        val builder: NavOptionsBuilder.() -> Unit = {},
        val isAuthorized: Boolean? = true,
    ) : NavigationCommand()

    /**
     * Back navigation command
     */
    data object Back : NavigationCommand()
}
