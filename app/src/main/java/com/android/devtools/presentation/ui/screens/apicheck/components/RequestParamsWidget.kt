package com.android.devtools.presentation.ui.screens.apicheck.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.devtools.domain.api.RequestConfigType
import com.android.devtools.domain.api.RequestParam
import com.android.devtools.presentation.ui.components.input.InputComponent

@Composable
fun RequestParamsWidget(
    type: RequestConfigType,
    modifier: Modifier,
    params: List<RequestParam>,
    onValueChange: (Int, RequestParam) -> Unit
) {
    when (type) {
        RequestConfigType.QUERY -> ParamsComponent(modifier = modifier, params = params, onValueChange = onValueChange)
        RequestConfigType.AUTHORIZATION -> AuthorizationComponent(modifier = modifier)
        RequestConfigType.HEADERS -> HeadersComponent(modifier = modifier)
        RequestConfigType.BODY -> BodyComponent(modifier = modifier)
    }
}

@Composable
fun ParamsComponent(
    modifier: Modifier = Modifier,
    params: List<RequestParam>,
    onValueChange: (Int, RequestParam) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        params.forEachIndexed { index, requestParam ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)) {
                InputComponent(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .padding(horizontal = 2.dp),
                    input = requestParam.key,
                    hint = "key",
                    onValueChange = {
                        onValueChange.invoke(index, RequestParam(key = it, value = requestParam.value))
                    }
                )
                InputComponent(
                    modifier = Modifier
                        .weight(1f)
                        .height(32.dp)
                        .padding(horizontal = 2.dp),
                    input = requestParam.value,
                    hint = "value",
                    onValueChange = {
                        onValueChange.invoke(index, RequestParam(key = requestParam.key, value = it))
                    }
                )
            }
        }
    }
}

@Composable
fun AuthorizationComponent(modifier: Modifier = Modifier) {
    
}

@Composable
fun HeadersComponent(modifier: Modifier = Modifier) {
    Text(text = "HeadersComponent")
}

@Composable
fun BodyComponent(modifier: Modifier = Modifier) {
    Text(text = "BodyComponent")
}