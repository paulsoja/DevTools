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

    data object RequestList : ApiCheckFlow(NavFlowRoute("RequestList"), "RequestList")
    data object CreateRequest : ApiCheckFlow(NavFlowRoute("CreateRequest"), "CreateRequest")
    data object ResponseOverview : ApiCheckFlow(
        flowRoute = NavFlowRoute(route = "ResponseOverview"),
        label = "ResponseOverview",
        argKey1 = "response_body"
    )
}