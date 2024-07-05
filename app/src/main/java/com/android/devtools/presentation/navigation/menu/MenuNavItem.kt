package com.android.devtools.presentation.navigation.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.devtools.presentation.navigation.NavFlow
import com.android.devtools.presentation.navigation.NavFlowRoute

abstract class MenuNavItem(
    @DrawableRes val foregroundIcon: Int,
    @DrawableRes val backgroundIcon: Int,
    @StringRes val title: Int,
    override val flowRoute: NavFlowRoute,
    override val label: String,
) : NavFlow()