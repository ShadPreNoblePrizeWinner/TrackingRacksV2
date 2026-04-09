package com.finistro.trackingracks.ui.screens.income

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.data.model.GigEntry
import com.finistro.trackingracks.ui.components.SteampunkButton
import com.finistro.trackingracks.ui.components.SteampunkCard
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.InputBg
import com.finistro.trackingracks.ui.theme.LabelBlue
import com.finistro.trackingracks.ui.theme.TextDark
import com.finistro.trackingracks.ui.theme.TextPrimary
import com.finistro.trackingracks.viewmodel.GigViewModel
import java.time.LocalDate

@Composable
fun IncomeScreen(
    viewModel: GigViewModel,
    onDone: () -> Unit
) {
    var offerAmount by remember { mutableStateOf("") }
    var distanceMiles by remember { mutableStateOf("") }
    var isDoubleOrder by remember { mutableStateOf(false) }
    var isAddOn by remember { mutableStateOf(false) }
    var weather by remember { mutableStateOf("Clear") }
    var temperature by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val weatherOptions = listOf("Clear", "Cloudy", "Rain", "Snow", "Stormy", "Foggy")
    val scrollState = rememberScrollState()

    val labelColor = LabelBlue
    val inputBg = InputBg
    val inputTextColor = Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InputBg)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text("Log Income", color = Brass, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 16.dp), fontSize = 26.sp)

        SteampunkCard {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = offerAmount,
                        onValueChange = { offerAmount = it },
                        label = { Text("Offer $", color = labelColor, fontSize = 14.sp) },
                        modifier = Modifier.weight(1f).height(IntrinsicSize.Min),
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
                    Spacer(Modifier.width(8.dp))
                    OutlinedTextField(
                        value = distanceMiles,
                        onValueChange = { distanceMiles = it },
                        label = { Text("Miles", color = labelColor, fontSize = 14.sp) },
                        modifier = Modifier.weight(1f).height(IntrinsicSize.Min),
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(
                            checked = isDoubleOrder,
                            onCheckedChange = { isDoubleOrder = it },
                            colors = CheckboxDefaults.colors(checkedColor = Brass)
                        )
                        Text("Double", color = TextDark, style = MaterialTheme.typography.bodySmall, fontSize = 14.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Checkbox(
                            checked = isAddOn,
                            onCheckedChange = { isAddOn = it },
                            colors = CheckboxDefaults.colors(checkedColor = Brass)
                        )
                        Text("Add-On", color = TextDark, style = MaterialTheme.typography.bodySmall, fontSize = 14.sp)
                    }
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = weather,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Weather", color = labelColor, fontSize = 14.sp) },
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
                        Box(modifier = Modifier.matchParentSize().clickable { expanded = true })

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.fillMaxWidth(0.45f)
                        ) {
                            weatherOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        weather = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(Modifier.width(8.dp))
                    OutlinedTextField(
                        value = temperature,
                        onValueChange = { temperature = it },
                        label = { Text("Temp °F", color = labelColor, fontSize = 14.sp) },
                        modifier = Modifier.weight(1f).height(IntrinsicSize.Min),
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

                Spacer(modifier = Modifier.height(16.dp))

                SteampunkButton(
                    text = "Save Entry",
                    onClick = {
                        val amount = offerAmount.toDoubleOrNull() ?: 0.0
                        val miles = distanceMiles.toDoubleOrNull() ?: 0.0
                        val temp = temperature.toDoubleOrNull() ?: 0.0
                        
                        val newEntry = GigEntry(
                            id = (System.currentTimeMillis() % Int.MAX_VALUE).toInt(),
                            offerAmount = amount,
                            distanceMiles = miles,
                            isDoubleOrder = isDoubleOrder,
                            isAddOn = isAddOn,
                            weather = weather,
                            weatherTemp = temp,
                            date = LocalDate.now().toString()
                        )
                        viewModel.addGig(newEntry)
                        onDone()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
