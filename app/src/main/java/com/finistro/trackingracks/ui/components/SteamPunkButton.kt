package com.finistro.trackingracks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.IronLight
import com.finistro.trackingracks.ui.theme.IronMedium
import com.finistro.trackingracks.ui.theme.NeonGreen

@Composable
fun SteampunkButton(
    text: String,
    modifier: Modifier = Modifier,
    glow: Boolean = false,
    onClick: () -> Unit
) {
    val bg = Brush.verticalGradient(listOf(IronLight, IronMedium))
    val borderColor = if (glow) NeonGreen else Brass

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(containerColor = IronMedium)
    ) {
        Box(
            modifier = Modifier
                .background(bg)
                .padding(8.dp)
        ) {
            Text(
                text = text,
                color = borderColor,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }
}
