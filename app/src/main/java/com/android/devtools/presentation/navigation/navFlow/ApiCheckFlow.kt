package com.android.devtools.presentation.navigation.navFlow

import com.android.devtools.presentation.navigation.NavFlow
import com.android.devtools.presentation.navigation.NavFlowRoute

abstract class ApiCheckFlow(
    override val flowRoute: NavFlowRoute,
    override val label: String,
    override val argKey1: String? = null,
    override val argKey2: String? = null,
    override val argKey3: String? = null,
) : NavFlow() {

    data object ApiCheck : ApiCheckFlow(NavFlowRoute("ApiCheck"), "ApiCheck")
}