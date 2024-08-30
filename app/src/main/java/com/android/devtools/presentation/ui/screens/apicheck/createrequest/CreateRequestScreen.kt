package com.android.devtools.presentation.ui.screens.apicheck.createrequest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.devtools.domain.api.RequestConfigType
import com.android.devtools.domain.api.RestApiMethodType
import com.android.devtools.domain.api.toStringParameter
import com.android.devtools.presentation.navigation.AppNavigator
import com.android.devtools.presentation.navigation.navFlow.ApiCheckFlow
import com.android.devtools.presentation.ui.components.input.InputComponent
import com.android.devtools.presentation.ui.components.pager.TabRowComponent
import com.android.devtools.presentation.ui.screens.apicheck.components.RequestParamsWidget

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateRequestScreen(appNavigator: AppNavigator, paddingValues: PaddingValues) {

    val viewModel = hiltViewModel<CreateRequestViewModel>()
    val requestParamsState by viewModel.requestParamsState.collectAsState()
    val methods: List<String> = RestApiMethodType.entries.map { it.type }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        InputComponent(
            input = "",
            hint = "https://example.com",
            onValueChange = { viewModel.setUrl(it) },
            startComponent = {
                Box(
                    modifier = Modifier.fillMaxHeight(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Row(
                        modifier = Modifier
                            .clickable { expanded = true }
                            .padding(vertical = 8.dp)
                            .padding(start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = methods[selectedIndex],
                            color = Color.Red.copy(alpha = 0.5f),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                        )
                        Icon(
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Red.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(Color.Gray)
                    ) {
                        methods.forEachIndexed { index, s ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = s)
                                },
                                onClick = {
                                    selectedIndex = index
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                VerticalDivider(
                    color = Color.DarkGray.copy(alpha = 0.5f),
                    thickness = 1.dp
                )
            },
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        val pagerState = rememberPagerState(
            initialPage = RequestConfigType.QUERY.ordinal,
            pageCount = { RequestConfigType.entries.size },
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            TabRowComponent(
                headers = RequestConfigType.entries,
                pagerState = pagerState,
                modifier = Modifier.zIndex(10f)
            )
            
            HorizontalPager(
                state = pagerState,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
            ) { index ->
                RequestParamsWidget(
                    type = RequestConfigType.entries[index],
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    params = requestParamsState,
                    onValueChange = viewModel::addRequestParam
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                appNavigator.navigateToFlowWithArgs(
                    navigationFlow = ApiCheckFlow.ResponseOverview,
                    arg = arrayOf(ApiCheckFlow.ResponseOverview.argKey1!! to requestParamsState.toStringParameter())
                )
            }
        ) {
            Text(text = "Send")
        }
    }
}