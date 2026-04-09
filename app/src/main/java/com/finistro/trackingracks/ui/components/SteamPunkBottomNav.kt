package com.finistro.trackingracks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.ContainerBg
import com.finistro.trackingracks.ui.theme.Copper
import com.finistro.trackingracks.ui.theme.LabelBlue
import com.finistro.trackingracks.ui.theme.NavBlue

@Composable
fun SteampunkBottomNav(
    current: String,
    onSelect: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(64.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(32.dp)),
        color = ContainerBg,
        shape = RoundedCornerShape(32.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Home", "Income", "Vehicle", "Expense").forEach { item ->
                val color = if (item == current) NavBlue else LabelBlue

                Text(
                    text = item,
                    color = color,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .clickable { onSelect(item) }
                        .padding(8.dp)
                )
            }
        }
    }
}
