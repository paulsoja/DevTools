package com.android.devtools.presentation.navigation

abstract class NavFlow {
    abstract val flowRoute: NavFlowRoute
    abstract val label: String
    open val argKey1: String? = null
    open val argKey2: String? = null
    open val argKey3: String? = null
}