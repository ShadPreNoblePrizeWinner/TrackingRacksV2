package com.finistro.trackingracks.ui.screens.addgig

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.data.model.GigEntry
import com.finistro.trackingracks.viewmodel.GigViewModel
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.ContainerBg
import com.finistro.trackingracks.ui.theme.InputBg
import com.finistro.trackingracks.ui.theme.LabelBlue
import com.finistro.trackingracks.ui.theme.TextDark
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
    var timeOfOffer by remember {
        mutableStateOf(
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        )
    }
    var isDoubleOrder by remember { mutableStateOf(false) }
    var acceptedCount by remember { mutableStateOf("1") }
    var declinedCount by remember { mutableStateOf("0") }
    var completedCount by remember { mutableStateOf("1") }
    var timeTaken by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var weather by remember { mutableStateOf("") }
    var weatherTemp by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    val labelColor = LabelBlue
    val inputBg = InputBg
    val inputTextColor = Color.Black
    ContainerBg // Shade darker than F7F7F7

    Column(modifier = Modifier
        .fillMaxSize()
        .background(InputBg)) {
        Column(
            Modifier
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = offer,
                    onValueChange = { offer = it },
                    label = { Text("Offer $", color = labelColor, fontSize = 14.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = inputTextColor,
                        unfocusedTextColor = inputTextColor,
                        focusedContainerColor = inputBg,
                        unfocusedContainerColor = inputBg,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                    )
                )
                Spacer(Modifier.width(4.dp))
                OutlinedTextField(
                    value = distance,
                    onValueChange = { distance = it },
                    label = { Text("Miles", color = labelColor, fontSize = 14.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = inputTextColor,
                        unfocusedTextColor = inputTextColor,
                        focusedContainerColor = inputBg,
                        unfocusedContainerColor = inputBg,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                    )
                )
            }

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("City", color = labelColor, fontSize = 16.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = weatherTemp,
                    onValueChange = { weatherTemp = it },
                    label = { Text("Temp °F", color = labelColor, fontSize = 14.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = inputTextColor,
                        unfocusedTextColor = inputTextColor,
                        focusedContainerColor = inputBg,
                        unfocusedContainerColor = inputBg,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                    )
                )
                Spacer(Modifier.width(4.dp))
                OutlinedTextField(
                    value = weather,
                    onValueChange = { weather = it },
                    label = { Text("Weather", color = labelColor, fontSize = 14.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = inputTextColor,
                        unfocusedTextColor = inputTextColor,
                        focusedContainerColor = inputBg,
                        unfocusedContainerColor = inputBg,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                    )
                )
            }

            OutlinedTextField(
                value = appNameUsed,
                onValueChange = { appNameUsed = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("App Name Used", color = labelColor, fontSize = 16.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )


            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date (YYYY-MM-DD)", color = labelColor, fontSize = 16.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = timeOfOffer,
                onValueChange = { timeOfOffer = it },
                label = { Text("Time of Offer (HH:mm)", color = labelColor, fontSize = 16.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
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
                Text("Double Order", color = TextDark, fontSize = 16.sp)
            }

            OutlinedTextField(
                value = acceptedCount,
                onValueChange = { acceptedCount = it },
                label = { Text("Accepted Count", color = labelColor, fontSize = 16.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = declinedCount,
                onValueChange = { declinedCount = it },
                label = { Text("Declined Count", color = labelColor, fontSize = 16.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = completedCount,
                onValueChange = { completedCount = it },
                label = { Text("Completed Count", color = labelColor, fontSize = 16.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = timeTaken,
                onValueChange = { timeTaken = it },
                label = { Text("Time Taken (mins)", color = labelColor, fontSize = 16.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
                    focusedBorderColor = Brass,
                    unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                )
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", color = labelColor, fontSize = 16.sp) },
                modifier = Modifier.height(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = inputTextColor,
                    unfocusedTextColor = inputTextColor,
                    focusedContainerColor = inputBg,
                    unfocusedContainerColor = inputBg,
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
                    val dayOfWeek =
                        parsedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

                    viewModel.addGig(
                        GigEntry(
                            offerAmount = offer.toDoubleOrNull() ?: 0.0,
                            distanceMiles = distance.toDoubleOrNull() ?: 0.0,
                            city = city,
                            appNameUsed = appNameUsed,
                            date = date,
                            dayOfWeek = dayOfWeek,
                            timeOfOffer = timeOfOffer,
                            weather = weather,
                            weatherTemp = weatherTemp.toDoubleOrNull() ?: 0.0,
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
