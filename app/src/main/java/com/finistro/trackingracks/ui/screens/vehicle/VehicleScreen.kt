package com.finistro.trackingracks.ui.screens.vehicle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.data.model.Vehicle
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.ContainerBg
import com.finistro.trackingracks.ui.theme.InputBg
import com.finistro.trackingracks.ui.theme.LabelBlue
import com.finistro.trackingracks.viewmodel.GigViewModel

@Composable
fun VehicleScreen(viewModel: GigViewModel) {
    val vehicles by viewModel.vehicles.collectAsState()
    
    var selectedVehicle by remember(vehicles) { 
        mutableStateOf(vehicles.firstOrNull() ?: Vehicle()) 
    }
    
    var nickname by remember(selectedVehicle) { mutableStateOf(selectedVehicle.nickname) }
    var make by remember(selectedVehicle) { mutableStateOf(selectedVehicle.make) }
    var model by remember(selectedVehicle) { mutableStateOf(selectedVehicle.model) }
    var year by remember(selectedVehicle) { mutableStateOf(selectedVehicle.year) }
    var mileage by remember(selectedVehicle) { mutableStateOf(selectedVehicle.mileage) }
    var mpg by remember(selectedVehicle) { mutableStateOf(selectedVehicle.mpg) }

    var expanded by remember { mutableStateOf(false) }
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

        // Vehicle Dropdown
        Box(modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable { expanded = true },
                color = ContainerBg,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(selectedVehicle.nickname, color = Brass)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Brass)
                }
            }
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f).background(ContainerBg)
            ) {
                vehicles.forEach { vehicle ->
                    DropdownMenuItem(
                        text = { Text(vehicle.nickname, color = Brass) },
                        onClick = {
                            selectedVehicle = vehicle
                            expanded = false
                        }
                    )
                }
                HorizontalDivider(color = Brass.copy(alpha = 0.3f))
                DropdownMenuItem(
                    text = { Text("+ Add New Vehicle", color = Brass) },
                    onClick = {
                        val newVehicle = Vehicle(nickname = "New Vehicle")
                        viewModel.addVehicle(newVehicle)
                        selectedVehicle = newVehicle
                        expanded = false
                    }
                )
            }
        }

        OutlinedTextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("Vehicle Nickname", color = labelColor, fontSize = 16.sp) },
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

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = make,
                onValueChange = { make = it },
                label = { Text("Make", color = labelColor, fontSize = 16.sp) },
                modifier = Modifier.weight(1f),
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
                modifier = Modifier.weight(1f),
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

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year", color = labelColor, fontSize = 16.sp) },
                modifier = Modifier.weight(1f),
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
                modifier = Modifier.weight(1f),
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

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SteampunkButton(
                text = "Save",
                glow = true,
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.updateVehicle(selectedVehicle.copy(
                        nickname = nickname,
                        make = make,
                        model = model,
                        year = year,
                        mileage = mileage,
                        mpg = mpg
                    ))
                }
            )
            
            if (vehicles.size > 1) {
                Button(
                    onClick = { viewModel.deleteVehicle(selectedVehicle.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.7f)),
                    modifier = Modifier.height(50.dp)
                ) {
                    Text("Delete", color = Color.White)
                }
            }
        }
    }
}
