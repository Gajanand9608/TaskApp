package com.example.demoproject2.commonUIWidgets

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun CommonTextView(text: String, style: TextStyle) {
    Text(
        text = text ,
        modifier = Modifier.padding(8.dp),
        style = style
    )
}

@Composable
fun CommonImageView(imageUrl: String?, modifier : Modifier) {
    if (imageUrl.isNullOrEmpty()) {
        LoadingState(modifier)
    } else {
        SubcomposeAsyncImage(
            model = imageUrl,
            loading = { LoadingState(modifier) },
            modifier = modifier,
            contentDescription = "image",
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFEDEDED)
            )
            .background(
                Color.Transparent,
                shape = RoundedCornerShape(14.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect()
        )
    }
}


fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }

    val transition = rememberInfiniteTransition(label = "")

    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        ), label = ""
    )

    border(
        width = 1.dp,
        shape = RoundedCornerShape(14.dp),
        color = Color.Transparent
    )

    background(
        shape = RoundedCornerShape(14.dp),
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5)
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width, size.height.toFloat())
        ),
    )
        .onGloballyPositioned {
            size = it.size
        }
}