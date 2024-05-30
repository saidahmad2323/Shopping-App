package com.example.signalapp.Components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val animatedValue = rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500), repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(modifier = modifier.background(Color.LightGray.copy(alpha = 0.5f))) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = animatedValue.value, clip = true
                )
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.LightGray.copy(alpha = 0.5f),
                            Color.LightGray.copy(alpha = 0.8f),
                            Color.LightGray.copy(alpha = 0.5f)
                        )
                    )
                )
        )
    }
}