package com.finistro.trackingracks.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.components.SteampunkCard
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.NeonGreen
import com.finistro.trackingracks.ui.theme.TextSecondary
import com.finistro.trackingracks.viewmodel.GigViewModel

@Composable
fun DashboardScreen(
    viewModel: GigViewModel,
    onGoToPreviousRacks: () -> Unit,
    onGoToExpensePage: () -> Unit
) {
    val gigs = viewModel.gigs.collectAsState().value

    val sevenDayHigh = gigs
        .takeLast(7)
        .maxOfOrNull { it.offerAmount }
        ?: 0.0

    val thirtyDayAverage = gigs
        .takeLast(30)
        .map { it.offerAmount }
        .average()
        .let { if (it.isNaN()) 0.0 else it }

    val topApp = gigs
        .groupBy { it.appNameUsed.ifBlank { "Unknown" } }
        .maxByOrNull { (_, entries) -> entries.sumOf { it.offerAmount } }
        ?.key
        ?: "N/A"

    val totalMileage = gigs.sumOf { it.distanceMiles }

    val thirtyDayAverageMileage = gigs
        .takeLast(30)
        .map { it.distanceMiles }
        .average()
        .let { if (it.isNaN()) 0.0 else it }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SteampunkCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("7 Day High", color = TextSecondary)
                    Text("$${"%.2f".format(sevenDayHigh)}", color = NeonGreen)
                }
            }

            SteampunkCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("30 Day Average", color = TextSecondary)
                    Text("$${"%.2f".format(thirtyDayAverage)}", color = Brass)
                }
            }

            SteampunkCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Most Profitable App", color = TextSecondary)
                    Text(topApp, color = Brass)
                }
            }

            SteampunkCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Total Mileage", color = TextSecondary)
                    Text("${"%.1f".format(totalMileage)} mi", color = Brass)
                }
            }

            SteampunkCard(modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("30 Day Avg Mileage", color = TextSecondary)
                    Text("${"%.1f".format(thirtyDayAverageMileage)} mi", color = Brass)
                }
            }

            Spacer(Modifier.height(8.dp))

            SteampunkButton(
                text = "Previous Racks",
                glow = true,
                modifier = Modifier.fillMaxWidth(),
                onClick = onGoToPreviousRacks
            )

            SteampunkButton(
                text = "Go to Expense Page",
                modifier = Modifier.fillMaxWidth(),
                onClick = onGoToExpensePage
            )
        }
    }
}
