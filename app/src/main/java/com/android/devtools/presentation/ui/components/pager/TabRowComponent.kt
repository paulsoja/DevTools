package com.android.devtools.presentation.ui.components.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.android.devtools.domain.api.RequestConfigType
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabRowComponent(
    modifier: Modifier = Modifier,
    headers: List<RequestConfigType>,
    pagerState: PagerState,
    containerColor: Color = Color.LightGray,
) {
    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = containerColor,
        edgePadding = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
    ) {
        headers.forEachIndexed { index, params ->
            TypeOfScreenTab(
                modifier = Modifier
                    .zIndex(1f),
                selected = pagerState.currentPage == index,
                typeOfScreen = params,
            ) {
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        }
    }
}

@Composable
fun TypeOfScreenTab(
    modifier: Modifier,
    selected: Boolean,
    typeOfScreen: RequestConfigType,
    selectedContentColor: Color = Color(0xFFCF007A),
    unselectedContentColor: Color = Color(0xFF424242),
    onClick: () -> Unit
) = Tab(
    selected = selected,
    modifier = modifier
        //.border(width = 1.dp, color = if (selected) Color.Blue else Color.Transparent, shape = MaterialTheme.shapes.small)
        .zIndex(2f),
    selectedContentColor = selectedContentColor,
    unselectedContentColor = unselectedContentColor,
    onClick = onClick,
    text = {
        Text(
            text = typeOfScreen.title,
            color = if (selected) selectedContentColor else unselectedContentColor,
        )
    }
)