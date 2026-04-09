package com.finistro.trackingracks.ui.screens.vehicle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.data.model.Vehicle
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.InputBg
import com.finistro.trackingracks.ui.theme.LabelBlue
import com.finistro.trackingracks.ui.theme.TextPrimary
import com.finistro.trackingracks.viewmodel.GigViewModel

@Composable
fun VehicleScreen(viewModel: GigViewModel) {
    val vehicle by viewModel.vehicle.collectAsState()
    
    var make by remember(vehicle) { mutableStateOf(vehicle.make) }
    var model by remember(vehicle) { mutableStateOf(vehicle.model) }
    var year by remember(vehicle) { mutableStateOf(vehicle.year) }
    var mileage by remember(vehicle) { mutableStateOf(vehicle.mileage) }
    var mpg by remember(vehicle) { mutableStateOf(vehicle.mpg) }

    val scrollState = rememberScrollState()

    val labelColor = LabelBlue
    val inputBg = InputBg
    val inputTextColor = Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InputBg)
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Vehicle Information", style = MaterialTheme.typography.headlineMedium, color = Brass, fontSize = 30.sp)

        OutlinedTextField(
            value = make,
            onValueChange = { make = it },
            label = { Text("Make", color = labelColor, fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
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
            value = model,
            onValueChange = { model = it },
            label = { Text("Model", color = labelColor, fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
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
            value = year,
            onValueChange = { year = it },
            label = { Text("Year", color = labelColor, fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
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
            value = mileage,
            onValueChange = { mileage = it },
            label = { Text("Mileage", color = labelColor, fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
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
            value = mpg,
            onValueChange = { mpg = it },
            label = { Text("MPG", color = labelColor, fontSize = 16.sp) },
            modifier = Modifier.fillMaxWidth(),
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
            text = "Save Vehicle",
            glow = true,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.updateVehicle(Vehicle(make, model, year, mileage, mpg))
            }
        )
    }
}
