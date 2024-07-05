package com.android.devtools.presentation.ui.screens.apicheck.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RequestItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 4.dp)) {
        Text(modifier = Modifier.padding(start = 8.dp, end = 16.dp), text = "code")
        Column {
            Text(text = "request")
            Text(text = "base url")
            Row {
                Text(text = "time")
                Text(text = "duration")
                Text(text = "size")
            }
        }
    }
}