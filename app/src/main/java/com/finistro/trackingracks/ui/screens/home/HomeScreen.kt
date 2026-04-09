package com.finistro.trackingracks.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.R
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.components.SteampunkCard
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.InputBg
import com.finistro.trackingracks.ui.theme.LabelBlue
import com.finistro.trackingracks.ui.theme.NeonGreen
import com.finistro.trackingracks.ui.theme.TextDark
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
    
    val dailyStats = rolling30.groupBy { it.date }.mapValues { (_, entries) ->
        val accepted = entries.sumOf { it.acceptedCount }
        val completed = entries.sumOf { it.completedCount }
        val declined = entries.sumOf { it.declinedCount }
        val miles = entries.sumOf { it.distanceMiles }
        val totalOffers = accepted + declined
        val completionRate = if (accepted > 0) (completed.toDouble() / accepted) * 100 else 0.0
        val totalAmount = entries.sumOf { it.offerAmount }
        Stats(accepted, declined, completionRate, miles, totalOffers, totalAmount)
    }

    val avgMiles = if (dailyStats.isNotEmpty()) dailyStats.values.sumOf { it.miles } / dailyStats.size else 0.0
    val avgOffers = if (dailyStats.isNotEmpty()) dailyStats.values.sumOf { it.totalOffers }.toDouble() / dailyStats.size else 0.0
    val avgCompletion = if (dailyStats.isNotEmpty()) dailyStats.values.sumOf { it.completionRate } / dailyStats.size else 0.0
    val avgIncome = if (dailyStats.isNotEmpty()) dailyStats.values.sumOf { it.totalAmount } / dailyStats.size else 0.0

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize().background(InputBg).verticalScroll(scrollState)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(140.dp)
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
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Rolling 30-Day Highlights",
                    color = Brass,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    )
                )
                
                // Best 3 Days and Income Avg
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    // Best 3 Days by Amount (Left)
                    Column(modifier = Modifier.weight(1.2f)) {
                        Text("Best 3 Days", color = LabelBlue, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        val sortedAmount = dailyStats.toList().sortedByDescending { it.second.totalAmount }
                        repeat(3) { index ->
                            if (index < sortedAmount.size) {
                                val (date, stats) = sortedAmount[index]
                                Text(
                                    text = "${formatDate(date)}: $${"%.2f".format(stats.totalAmount)}",
                                    color = TextDark,
                                    fontSize = 16.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip
                                )
                            } else {
                                Text("---", color = TextDark.copy(alpha = 0.3f), fontSize = 16.sp)
                            }
                        }
                    }

                    // 30 Day Income Average (Right)
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.End
                    ) {
                        StatSummaryItem("30d Avg Income", avgIncome, "$", isPrefix = true)
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Top Lists Row (Top Miles, Top Offers, Top Comp)
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Top 3 Days by Miles
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Top Miles", color = LabelBlue, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        val sortedMilesDays = dailyStats.toList().sortedByDescending { it.second.miles }
                        repeat(3) { index ->
                            if (index < sortedMilesDays.size) {
                                val (date, stats) = sortedMilesDays[index]
                                Text(
                                    text = "${formatDate(date)}: ${"%.1f".format(stats.miles)}",
                                    color = TextDark,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip
                                )
                            } else {
                                Text("---", color = TextDark.copy(alpha = 0.3f), fontSize = 15.sp)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        StatSummaryItem("Avg Miles", avgMiles, "mi")
                    }

                    // Top 3 Days by Offers
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Top Offers", color = LabelBlue, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        val sortedOfferCountDays = dailyStats.toList().sortedByDescending { it.second.totalOffers }
                        repeat(3) { index ->
                            if (index < sortedOfferCountDays.size) {
                                val (date, stats) = sortedOfferCountDays[index]
                                Text(
                                    text = "${formatDate(date)}: ${stats.totalOffers}",
                                    color = TextDark,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip
                                )
                            } else {
                                Text("---", color = TextDark.copy(alpha = 0.3f), fontSize = 15.sp)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        StatSummaryItem("Avg Offers", avgOffers, "")
                    }

                    // Top 3 Completion Rates
                    Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Top Comp", color = LabelBlue, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        val sortedCompDays = dailyStats.toList().sortedByDescending { it.second.completionRate }
                        repeat(3) { index ->
                            if (index < sortedCompDays.size) {
                                val (date, stats) = sortedCompDays[index]
                                Text(
                                    text = "${formatDate(date)}: ${stats.completionRate.toInt()}%",
                                    color = TextDark,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Clip
                                )
                            } else {
                                Text("---", color = TextDark.copy(alpha = 0.3f), fontSize = 15.sp)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        StatSummaryItem("Avg Comp", avgCompletion, "%")
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SteampunkButton(
                text = "Add Gig",
                glow = true,
                modifier = Modifier.weight(1f),
                onClick = onAddGig
            )
            SteampunkButton(
                text = "Expense",
                glow = true,
                modifier = Modifier.weight(1f),
                onClick = { onNavigate("Expense") }
            )
            SteampunkButton(
                text = "Offers",
                glow = true,
                modifier = Modifier.weight(1f),
                onClick = { onNavigate("Offers") }
            )
        }
    }
}

@Composable
private fun StatSummaryItem(label: String, avgValue: Double, unit: String, isPrefix: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = LabelBlue, fontSize = 15.sp)
        val valueStr = if (avgValue % 1 == 0.0) avgValue.toInt().toString() else "%.1f".format(avgValue)
        val displayStr = if (isPrefix) "$unit$valueStr" else "$valueStr$unit"
        Text(
            text = displayStr,
            color = TextDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}

private data class Stats(
    val accepted: Int,
    val declined: Int,
    val completionRate: Double,
    val miles: Double,
    val totalOffers: Int,
    val totalAmount: Double
)

private fun formatDate(date: String): String {
    return try {
        val parts = date.split("-")
        if (parts.size >= 3) "${parts[1]}/${parts[2]}" else date.replace("-", "/")
    } catch (e: Exception) {
        date.replace("-", "/")
    }
}
