package com.android.devtools.presentation.navigation.menu

import com.android.devtools.R
import com.android.devtools.presentation.navigation.toFlowRoute

sealed class MenuNavItemsFlow {

    data object ApiCheck : MenuNavItem(
        R.drawable.ic_round_api_24,
        R.drawable.ic_round_api_24,
        R.string.menu_item_api_check,
        "ApiCheckFlow".toFlowRoute(),
        "ApiCheck"
    )
}