package com.finistro.trackingracks.ui.screens.heatmap

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.NeonGreen
import com.finistro.trackingracks.ui.theme.NeonRed
import kotlin.math.min

@Composable
fun HeatmapScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        val rows = 10
        val cols = 10

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            repeat(rows) { r ->
                Row(Modifier.fillMaxWidth()) {
                    repeat(cols) { c ->
                        val intensity = (r + c).toFloat() / (rows + cols)
                        val color = lerpColor(NeonGreen, NeonRed, intensity)

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(color)
                                .border(1.dp, Brass)
                        )
                    }
                }
            }
        }
    }
}

fun lerpColor(
    start: androidx.compose.ui.graphics.Color,
    end: androidx.compose.ui.graphics.Color,
    t: Float
): androidx.compose.ui.graphics.Color {
    val clamped = min(1f, maxOf(0f, t))
    return androidx.compose.ui.graphics.Color(
        red = start.red + (end.red - start.red) * clamped,
        green = start.green + (end.green - start.green) * clamped,
        blue = start.blue + (end.blue - start.blue) * clamped,
        alpha = 1f
    )
}
