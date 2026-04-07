package com.finistro.trackingracks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.IronLight
import com.finistro.trackingracks.ui.theme.IronMedium

@Composable
fun SteampunkCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = IronMedium
        ),
        border = BorderStroke(2.dp, Brass),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(IronMedium, IronLight)
                    )
                )
                .padding(16.dp)
        ) {
            content()
        }
    }
}
