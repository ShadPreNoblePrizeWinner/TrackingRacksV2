package com.finistro.trackingracks.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.components.SteampunkCard
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.NeonGreen
import com.finistro.trackingracks.ui.theme.TextSecondary
import com.finistro.trackingracks.viewmodel.GigViewModel

@Composable
fun HomeScreen(
    viewModel: GigViewModel,
    onAddGig: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val gigs = viewModel.gigs.collectAsState()
    val rolling30 = gigs.value.takeLast(30)
    val best3Days = rolling30.sortedByDescending { it.offerAmount }.take(3)
    val mostMilesGig = rolling30.maxByOrNull { it.distanceMiles }
    val offerCount = rolling30.size

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(200.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Brass.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text("IMAGE PLACEHOLDER", color = Brass)
        }

        SteampunkCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("Rolling 30-Day Highlights", color = Brass)
                Column {
                    Text("Best 3 Days:", color = NeonGreen)
                    if (best3Days.isEmpty()) {
                        Text("N/A", color = NeonGreen)
                    } else {
                        best3Days.forEach { gig ->
                            Text(
                                "${gig.appNameUsed.ifBlank { "Unknown App" }} ${gig.dayOfWeek.ifBlank { "Unknown day" }} ${gig.date.ifBlank { "(no date)" }}: $${"%.2f".format(gig.offerAmount)}",
                                color = NeonGreen
                            )
                        }
                    }
                }
                Text(
                    "Most Miles: ${mostMilesGig?.distanceMiles?.let { "%.1f".format(it) } ?: 0.0} mi",
                    color = TextSecondary
                )
                Text("Offer Count: $offerCount", color = TextSecondary)
            }
        }

        SteampunkButton(
            text = "Add Gig",
            glow = true,
            modifier = Modifier.padding(16.dp),
            onClick = onAddGig
        )
    }
}
