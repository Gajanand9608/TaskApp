package com.example.demoproject2.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.demoproject2.commonUIWidgets.CommonElevatedCard
import com.example.demoproject2.model.Response

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            items(data) {
                CommonElevatedCard(it)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
