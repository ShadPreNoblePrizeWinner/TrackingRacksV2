package com.finistro.trackingracks.ui.screens.vehicle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.data.model.Vehicle
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.theme.Brass
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Vehicle Information", style = MaterialTheme.typography.headlineMedium, color = Brass)

        OutlinedTextField(
            value = make,
            onValueChange = { make = it },
            label = { Text("Make", color = TextPrimary) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Brass,
                unfocusedBorderColor = Brass.copy(alpha = 0.5f)
            )
        )

        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Model", color = TextPrimary) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Brass,
                unfocusedBorderColor = Brass.copy(alpha = 0.5f)
            )
        )

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year", color = TextPrimary) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Brass,
                unfocusedBorderColor = Brass.copy(alpha = 0.5f)
            )
        )

        OutlinedTextField(
            value = mileage,
            onValueChange = { mileage = it },
            label = { Text("Mileage", color = TextPrimary) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Brass,
                unfocusedBorderColor = Brass.copy(alpha = 0.5f)
            )
        )

        OutlinedTextField(
            value = mpg,
            onValueChange = { mpg = it },
            label = { Text("MPG", color = TextPrimary) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
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
