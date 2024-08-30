package com.android.devtools.presentation.ui.screens.apicheck.requestlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.devtools.presentation.navigation.AppNavigator
import com.android.devtools.presentation.ui.screens.apicheck.components.RequestItem

@Composable
fun RequestListScreen(appNavigator: AppNavigator, paddingValues: PaddingValues) {

    val viewModel = hiltViewModel<RequestListViewModel>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color.Blue)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(100) {
            RequestItem(onClick = { /* do something */ })
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Red
            )
        }
    }
}