package com.finistro.trackingracks.ui.screens.addgig

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.data.model.GigEntry
import com.finistro.trackingracks.viewmodel.GigViewModel
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.TextPrimary
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
    var acceptedCount by remember { mutableStateOf("1") }
    var declinedCount by remember { mutableStateOf("0") }
    var completedCount by remember { mutableStateOf("1") }
    var timeTaken by remember { mutableStateOf("") }
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
                label = { Text("Offer Amount", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = distance,
                onValueChange = { distance = it },
                label = { Text("Distance (miles)", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = appNameUsed,
                onValueChange = { appNameUsed = it },
                label = { Text("App Name Used", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date (YYYY-MM-DD)", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = timeOfOffer,
                onValueChange = { timeOfOffer = it },
                label = { Text("Time of Offer (HH:mm)", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isDoubleOrder,
                    onCheckedChange = { isDoubleOrder = it },
                    colors = CheckboxDefaults.colors(checkedColor = Brass)
                )
                Text("Double Order", color = Color.White)
            }

            OutlinedTextField(
                value = acceptedCount,
                onValueChange = { acceptedCount = it },
                label = { Text("Accepted Count", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = declinedCount,
                onValueChange = { declinedCount = it },
                label = { Text("Declined Count", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = completedCount,
                onValueChange = { completedCount = it },
                label = { Text("Completed Count", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = timeTaken,
                onValueChange = { timeTaken = it },
                label = { Text("Time Taken (mins)", color = TextPrimary) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", color = TextPrimary) },
                modifier = Modifier.height(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
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
                            acceptedCount = acceptedCount.toIntOrNull() ?: 0,
                            declinedCount = declinedCount.toIntOrNull() ?: 0,
                            completedCount = completedCount.toIntOrNull() ?: 0,
                            timeTaken = timeTaken.toIntOrNull() ?: 0,
                            description = description
                        )
                    )
                    onDone()
                }
            )
        }
    }
}
