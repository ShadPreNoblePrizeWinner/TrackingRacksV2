package com.finistro.trackingracks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.Copper
import com.finistro.trackingracks.ui.theme.IronDark
import com.finistro.trackingracks.ui.theme.NeonGreen

@Composable
fun SteampunkBottomNav(
    current: String,
    onSelect: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(IronDark)
            .border(2.dp, Copper),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf("Home", "Dashboard", "Heatmap", "Expense").forEach { item ->
            val color = if (item == current) NeonGreen else Brass

            Text(
                text = item,
                color = color,
                modifier = Modifier
                    .clickable { onSelect(item) }
                    .padding(8.dp)
            )
        }
    }
}
