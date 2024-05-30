package com.example.signalapp.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TabbedPager(
    modifier: Modifier = Modifier,
    titles: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    content: @Composable (Int) -> Unit,
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.Transparent,
            indicator = { tabPositions ->

            },
            divider = {},
            contentColor = Color.Transparent
        ) {
            titles.forEachIndexed { index, title ->
                val textColor = if (selectedTabIndex == index) Color.White else Color.Black
                Box(
                    modifier = Modifier.clip(shape = RoundedCornerShape(30.dp))
                ) {
                    Tab(modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .background(
                            color = if (selectedTabIndex == index) Color.Blue
                            else Color.LightGray, shape = RoundedCornerShape(20.dp)
                        ),
                        text = { Text(text = title, color = textColor) },
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) })
                }

            }
        }
        Box {
            content(selectedTabIndex)
        }
    }
}