package com.finistro.trackingracks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.IronMedium
import com.finistro.trackingracks.ui.theme.NeonGreen
import com.finistro.trackingracks.ui.theme.NeonRed

@Composable
fun SteampunkToggle(
    label: String,
    initial: Boolean = false,
    onToggle: (Boolean) -> Unit
) {
    var state by remember { mutableStateOf(initial) }

    val color = if (state) NeonGreen else NeonRed

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Brass)
            .background(IronMedium)
            .clickable {
                state = !state
                onToggle(state)
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Brass, modifier = Modifier.weight(1f))
        Text(text = if (state) "ON" else "OFF", color = color)
    }
}
