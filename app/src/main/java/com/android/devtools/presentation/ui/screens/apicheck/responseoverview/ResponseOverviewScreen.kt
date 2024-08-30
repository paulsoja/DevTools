package com.android.devtools.presentation.ui.screens.apicheck.responseoverview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.devtools.domain.api.RequestParam
import com.android.devtools.presentation.navigation.AppNavigator
import com.android.devtools.presentation.ui.components.topbar.DefaultTopBar

@Composable
fun ResponseOverviewScreen(
    appNavigator: AppNavigator,
    paddingValues: PaddingValues,
    requestParams: List<RequestParam>
) {

    val viewModel = hiltViewModel<ResponseOverviewViewModel>()

    LaunchedEffect(key1 = Unit) {
        viewModel.trySendRequest(list = requestParams, url = "", requestType = "")
    }

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        topBar = {
            DefaultTopBar(
                title = "Response Overview",
                navigation = { appNavigator.back() },
                action = {  },
            )
        },
        content = { paddings ->
            Content(modifier = Modifier.padding(paddings), requestParams = requestParams)
        }
    )
}

@Composable
private fun Content(modifier: Modifier = Modifier, requestParams: List<RequestParam>) {
    Text(text = requestParams.size.toString())
}