package com.finistro.trackingracks.ui.screens.addgig

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.data.model.GigEntry
import com.finistro.trackingracks.viewmodel.GigViewModel
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.theme.Brass
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun AddGigScreen(viewModel: GigViewModel, onDone: () -> Unit) {

    var offer by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var appNameUsed by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var timeOfOffer by remember { mutableStateOf(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))) }
    var isDoubleOrder by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {

            OutlinedTextField(
                value = offer,
                onValueChange = { offer = it },
                label = { Text("Offer Amount") }
            )

            OutlinedTextField(
                value = distance,
                onValueChange = { distance = it },
                label = { Text("Distance (miles)") }
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") }
            )

            OutlinedTextField(
                value = appNameUsed,
                onValueChange = { appNameUsed = it },
                label = { Text("App Name Used") }
            )

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date (YYYY-MM-DD)") }
            )

            OutlinedTextField(
                value = timeOfOffer,
                onValueChange = { timeOfOffer = it },
                label = { Text("Time of Offer (HH:mm)") }
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isDoubleOrder,
                    onCheckedChange = { isDoubleOrder = it },
                    colors = CheckboxDefaults.colors(checkedColor = Brass)
                )
                Text("Double Order", color = Brass)
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.height(100.dp)
            )

            Spacer(Modifier.height(16.dp))

            SteampunkButton(
                text = "Save",
                glow = true,
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    val parsedDate = try {
                        LocalDate.parse(date)
                    } catch (e: Exception) {
                        LocalDate.now()
                    }
                    val dayOfWeek = parsedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

                    viewModel.addGig(
                        GigEntry(
                            offerAmount = offer.toDoubleOrNull() ?: 0.0,
                            distanceMiles = distance.toDoubleOrNull() ?: 0.0,
                            city = city,
                            appNameUsed = appNameUsed,
                            date = date,
                            dayOfWeek = dayOfWeek,
                            timeOfOffer = timeOfOffer,
                            isDoubleOrder = isDoubleOrder,
                            description = description
                        )
                    )
                    onDone()
                }
            )
        }
    }
}
