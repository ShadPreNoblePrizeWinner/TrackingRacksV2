package com.finistro.trackingracks.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.R
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
    val validGigs = gigs.value.filter { it.date.isNotBlank() }
    val rolling30 = validGigs.takeLast(30)
    val best3Days = rolling30.sortedByDescending { it.offerAmount }.take(3)
    val offerCount = rolling30.size

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(200.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Brass.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.gig1),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        SteampunkCard(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("Rolling 30-Day Highlights", color = Brass)
                
                // Best 3 Days by Amount
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Best 3 Days (Amount)", color = NeonGreen)
                    repeat(3) { index ->
                        val sortedAmount = rolling30.sortedByDescending { it.offerAmount }
                        if (index < sortedAmount.size) {
                            val gig = sortedAmount[index]
                            val dateStr = formatDate(gig.date)
                            Text(
                                "$dateStr | ${"%.1f".format(gig.distanceMiles)} mi | ${gig.weather.ifBlank { "Clear" }}",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                            Text(
                                "$${"%.2f".format(gig.offerAmount)}",
                                color = NeonGreen,
                                fontSize = 16.sp
                            )
                        } else {
                            Text("---", color = TextSecondary.copy(alpha = 0.3f))
                        }
                    }
                }

                val dailyStats = rolling30.groupBy { it.date }.mapValues { (_, entries) ->
                    val accepted = entries.sumOf { it.acceptedCount }
                    val completed = entries.sumOf { it.completedCount }
                    val declined = entries.sumOf { it.declinedCount }
                    val miles = entries.sumOf { it.distanceMiles }
                    val totalOffers = accepted + declined
                    val completionRate = if (accepted > 0) (completed.toDouble() / accepted) * 100 else 0.0
                    Stats(accepted, declined, completionRate, miles, totalOffers)
                }

                // Top 3 Days by Miles
                val sortedMilesDays = dailyStats.toList()
                    .sortedByDescending { it.second.miles }

                Spacer(Modifier.height(8.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Miles", color = Brass)
                    repeat(3) { index ->
                        if (index < sortedMilesDays.size) {
                            val (date, stats) = sortedMilesDays[index]
                            Text(
                                "${formatDate(date)}: ${"%.1f".format(stats.miles)} mi",
                                color = TextSecondary
                            )
                        } else {
                            Text("---", color = TextSecondary.copy(alpha = 0.3f))
                        }
                    }
                }

                // Top 3 Days by Offer Count
                val sortedOfferCountDays = dailyStats.toList()
                    .sortedByDescending { it.second.totalOffers }

                Spacer(Modifier.height(8.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Offer Count", color = Brass)
                    repeat(3) { index ->
                        if (index < sortedOfferCountDays.size) {
                            val (date, stats) = sortedOfferCountDays[index]
                            Text(
                                "${formatDate(date)}: ${stats.totalOffers} offers",
                                color = TextSecondary
                            )
                        } else {
                            Text("---", color = TextSecondary.copy(alpha = 0.3f))
                        }
                    }
                }

                // Top 3 Completion Rates
                val sortedCompletionDays = dailyStats.toList()
                    .sortedByDescending { it.second.completionRate }

                Spacer(Modifier.height(8.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text("Completion Rate", color = Brass)
                    repeat(3) { index ->
                        if (index < sortedCompletionDays.size) {
                            val (date, stats) = sortedCompletionDays[index]
                            Text(
                                "${formatDate(date)}: ${"%.1f".format(stats.completionRate)}% (Acc: ${stats.accepted}, Dec: ${stats.declined})",
                                color = TextSecondary
                            )
                        } else {
                            Text("---", color = TextSecondary.copy(alpha = 0.3f))
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    "Total Offers (30): $offerCount",
                    color = TextSecondary,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
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

private data class Stats(
    val accepted: Int,
    val declined: Int,
    val completionRate: Double,
    val miles: Double,
    val totalOffers: Int
)

private fun formatDate(date: String): String {
    return try {
        val parts = date.split("-")
        if (parts.size >= 3) "${parts[1]}/${parts[2]}" else date.replace("-", "/")
    } catch (e: Exception) {
        date.replace("-", "/")
    }
}
