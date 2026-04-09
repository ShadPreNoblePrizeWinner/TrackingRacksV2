package com.finistro.trackingracks.ui.screens.addgig

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import com.finistro.trackingracks.ui.theme.InputBg
import com.finistro.trackingracks.ui.theme.LabelBlue
import com.finistro.trackingracks.ui.theme.TextDark
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AddGigScreen(viewModel: GigViewModel, onDone: () -> Unit) {

    var offer by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    
    val apps = listOf("DoorDash", "UberEats", "Grubhub", "Instacart", "Spark", "Roadie", "Other")
    var appNameUsed by remember { mutableStateOf("") }
    var appExpanded by remember { mutableStateOf(false) }

    var appCrashed by remember { mutableStateOf(false) }
    val crashReasons = listOf("App Frozen", "GPS Issues", "Payment Error", "Login Issue", "Server Down", "Other")
    var selectedCrashReason by remember { mutableStateOf("") }
    var crashExpanded by remember { mutableStateOf(false) }

    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    var dayOfWeek by remember { 
        mutableStateOf(LocalDate.now().dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }) 
    }
    var dayExpanded by remember { mutableStateOf(false) }

    val timesOfDay = listOf("Morning", "Lunch", "Dinner", "Late Night")
    var timeOfDay by remember { mutableStateOf("") }
    var timeOfDayExpanded by remember { mutableStateOf(false) }

    val waitTimes = listOf("<5", "<10", "<15", "Too Long")
    var waitTime by remember { mutableStateOf("") }
    var waitTimeExpanded by remember { mutableStateOf(false) }

    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var timeOfOffer by remember {
        mutableStateOf(
            LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        )
    }
    var isDoubleOrder by remember { mutableStateOf(false) }
    var timeTaken by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var weather by remember { mutableStateOf("") }
    var weatherTemp by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    val labelColor = LabelBlue
    val inputBg = InputBg
    val inputTextColor = Color.Black

    Column(modifier = Modifier
        .fillMaxSize()
        .background(InputBg)) {
        Column(
            Modifier
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = appNameUsed,
                        onValueChange = { appNameUsed = it },
                        label = { Text("App Used", color = labelColor, fontSize = 16.sp) },
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, "Drop Down", tint = Brass, modifier = Modifier.clickable { appExpanded = true })
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = inputTextColor,
                            unfocusedTextColor = inputTextColor,
                            focusedContainerColor = inputBg,
                            unfocusedContainerColor = inputBg,
                            focusedBorderColor = Brass,
                            unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                        )
                    )
                    DropdownMenu(expanded = appExpanded, onDismissRequest = { appExpanded = false }) {
                        apps.forEach { app ->
                            DropdownMenuItem(text = { Text(app) }, onClick = { appNameUsed = app; appExpanded = false })
                        }
                    }
                }
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = offer,
                    onValueChange = { offer = it },
                    label = { Text("Offer ($)", color = labelColor, fontSize = 16.sp) },
                    modifier = Modifier.weight(0.6f).height(IntrinsicSize.Min),
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

            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = appCrashed,
                    onCheckedChange = { appCrashed = it },
                    colors = CheckboxDefaults.colors(checkedColor = Brass)
                )
                Text("App Crashed/Issue", color = TextDark, fontSize = 16.sp)
            }

            if (appCrashed) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = selectedCrashReason,
                        onValueChange = { selectedCrashReason = it },
                        label = { Text("Crash Reason", color = labelColor, fontSize = 16.sp) },
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, "Drop Down", tint = Brass, modifier = Modifier.clickable { crashExpanded = true })
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = inputTextColor,
                            unfocusedTextColor = inputTextColor,
                            focusedContainerColor = inputBg,
                            unfocusedContainerColor = inputBg,
                            focusedBorderColor = Brass,
                            unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                        )
                    )
                    DropdownMenu(expanded = crashExpanded, onDismissRequest = { crashExpanded = false }) {
                        crashReasons.forEach { reason ->
                            DropdownMenuItem(text = { Text(reason) }, onClick = { selectedCrashReason = reason; crashExpanded = false })
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            OutlinedTextField(
                value = distance,
                onValueChange = { distance = it },
                label = { Text("Distance (Miles)", color = labelColor, fontSize = 16.sp) },
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
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
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = dayOfWeek,
                        onValueChange = { dayOfWeek = it },
                        label = { Text("Day", color = labelColor, fontSize = 16.sp) },
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, "Drop Down", tint = Brass, modifier = Modifier.clickable { dayExpanded = true })
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = inputTextColor,
                            unfocusedTextColor = inputTextColor,
                            focusedContainerColor = inputBg,
                            unfocusedContainerColor = inputBg,
                            focusedBorderColor = Brass,
                            unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                        )
                    )
                    DropdownMenu(expanded = dayExpanded, onDismissRequest = { dayExpanded = false }) {
                        daysOfWeek.forEach { day ->
                            DropdownMenuItem(text = { Text(day) }, onClick = { dayOfWeek = day; dayExpanded = false })
                        }
                    }
                }
                Spacer(Modifier.width(8.dp))
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)", color = labelColor, fontSize = 16.sp) },
                    modifier = Modifier.weight(1.2f).height(IntrinsicSize.Min),
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

            Spacer(Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = timeOfDay,
                        onValueChange = { timeOfDay = it },
                        label = { Text("Time of Day", color = labelColor, fontSize = 16.sp) },
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, "Drop Down", tint = Brass, modifier = Modifier.clickable { timeOfDayExpanded = true })
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = inputTextColor,
                            unfocusedTextColor = inputTextColor,
                            focusedContainerColor = inputBg,
                            unfocusedContainerColor = inputBg,
                            focusedBorderColor = Brass,
                            unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                        )
                    )
                    DropdownMenu(expanded = timeOfDayExpanded, onDismissRequest = { timeOfDayExpanded = false }) {
                        timesOfDay.forEach { time ->
                            DropdownMenuItem(text = { Text(time) }, onClick = { timeOfDay = time; timeOfDayExpanded = false })
                        }
                    }
                }
                Spacer(Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = waitTime,
                        onValueChange = { waitTime = it },
                        label = { Text("Wait Time", color = labelColor, fontSize = 16.sp) },
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, "Drop Down", tint = Brass, modifier = Modifier.clickable { waitTimeExpanded = true })
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = inputTextColor,
                            unfocusedTextColor = inputTextColor,
                            focusedContainerColor = inputBg,
                            unfocusedContainerColor = inputBg,
                            focusedBorderColor = Brass,
                            unfocusedBorderColor = Brass.copy(alpha = 0.5f)
                        )
                    )
                    DropdownMenu(expanded = waitTimeExpanded, onDismissRequest = { waitTimeExpanded = false }) {
                        waitTimes.forEach { time ->
                            DropdownMenuItem(text = { Text(time) }, onClick = { waitTime = time; waitTimeExpanded = false })
                        }
                    }
                }
            }

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City", color = labelColor, fontSize = 16.sp) },
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
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
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
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
                value = weather,
                onValueChange = { weather = it },
                label = { Text("Weather", color = labelColor, fontSize = 16.sp) },
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
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
                value = weatherTemp,
                onValueChange = { weatherTemp = it },
                label = { Text("Weather Temp (°F)", color = labelColor, fontSize = 16.sp) },
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
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
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
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
                value = timeTaken,
                onValueChange = { timeTaken = it },
                label = { Text("Time Taken (mins)", color = labelColor, fontSize = 16.sp) },
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
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
                modifier = Modifier.height(100.dp).fillMaxWidth(),
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
                    viewModel.addGig(
                        GigEntry(
                            offerAmount = offer.toDoubleOrNull() ?: 0.0,
                            distanceMiles = distance.toDoubleOrNull() ?: 0.0,
                            city = city,
                            appNameUsed = appNameUsed,
                            acceptedCount = 1,
                            declinedCount = 0,
                            completedCount = 1,
                            date = date,
                            dayOfWeek = dayOfWeek,
                            timeOfOffer = timeOfOffer,
                            weather = weather,
                            weatherTemp = weatherTemp.toDoubleOrNull() ?: 0.0,
                            isDoubleOrder = isDoubleOrder,
                            timeTaken = timeTaken.toIntOrNull() ?: 0,
                            description = if (appCrashed) "[Crash: $selectedCrashReason] $description" else description
                        )
                    )
                    onDone()
                }
            )
        }
    }
}
