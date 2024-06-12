package com.example.demoproject2.commonUIWidgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoproject2.model.DataItem

@Composable
fun CommonElevatedCard(dataItem: DataItem) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        CommonTextView(
            dataItem.title ?: "No Title",
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        )
        CommonTextView(
            dataItem.price.value.display ?: "Price not available",
            TextStyle(
                fontSize = 14.sp,
                color = Color.Blue,
                fontWeight = FontWeight.Normal
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CommonImageView(
                imageUrl = dataItem.images[0].big.url,
                modifier = Modifier
                    .size(200.dp)
                    .padding(10.dp)
            )
        }
    }
}
