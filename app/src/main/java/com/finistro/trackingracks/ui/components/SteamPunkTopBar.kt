package com.finistro.trackingracks.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.IronDark
import com.finistro.trackingracks.ui.theme.IronMedium

@Composable
fun SteampunkTopBar(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(IronDark, IronMedium)
                )
            )
            .border(2.dp, Brass)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = title, color = Brass)
    }
}
