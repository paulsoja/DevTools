package com.android.devtools.presentation.ui.components.topbar

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigation: () -> Unit,
    action: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier.systemBarsPadding(),
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = navigation) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = action) {
                Icon(Icons.Default.Share, contentDescription = null)
            }
        }
    )
}