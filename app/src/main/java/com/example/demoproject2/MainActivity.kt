package com.example.demoproject2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.demoproject2.model.Response
import com.example.demoproject2.ui.theme.DemoProject2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoProject2Theme {
                val response = viewModel.olxDataState.value
                val showLoader = viewModel.showLoader.observeAsState()
                val showErrorMessage = viewModel.showErrorMessage.observeAsState()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) { innerPadding ->

                    if (showErrorMessage.value != null && showErrorMessage.value.isNullOrEmpty()
                            .not()
                    ) {
                        Toast.makeText(this, showErrorMessage.value, Toast.LENGTH_SHORT).show()
                    }
                    HomePage(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding), showLoader = showLoader.value, response
                    )
                }
            }
        }
    }
}

@Composable
fun HomePage(modifier: Modifier, showLoader: Boolean?, response: Response?) {
    if (showLoader != null && showLoader == true) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    AnimatedVisibility(
        visible = (response != null && showLoader != null && showLoader == false),
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        val data = response!!.data
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)) {
            items(data) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    CommonTextView(it.title, style = TextStyle(fontSize = 18.sp, color = Color.Red, fontWeight = FontWeight.Bold))
                    CommonTextView(
                        it.price.value.display,
                        TextStyle(fontSize = 14.sp, color = Color.Blue, fontWeight = FontWeight.Normal)
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        CommonImageView(
                            imageUrl = it.images[0].big.url,
                            modifier = Modifier
                                .size(200.dp)
                                .padding(10.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


@Composable
fun CommonTextView(text: String? = null, style: TextStyle) {
    Text(
        text = text ?: "No Title",
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
            contentDescription = "",
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
