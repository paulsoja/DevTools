package com.android.devtools.presentation.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputComponent(
    modifier: Modifier = Modifier,
    input: String,
    hint: String,
    startComponent: @Composable (RowScope.() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    var value by remember { mutableStateOf(input) }

    BasicTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
            value = it
        },
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .background(color = Color.LightGray),
        enabled = true,
        singleLine = false,
        textStyle = TextStyle.Default.copy(
            color = Color.DarkGray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        cursorBrush = SolidColor(Color.DarkGray),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Uri,
            imeAction = ImeAction.Done
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {

                startComponent?.invoke(this)

                Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = Color.DarkGray.copy(alpha = 0.5f),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,

                            )
                    }

                    innerTextField.invoke()
                }
            }
        }
    )
}