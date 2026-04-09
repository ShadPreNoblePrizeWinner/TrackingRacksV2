package com.finistro.trackingracks.ui.screens.expense

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.data.model.DailyExpense
import com.finistro.trackingracks.data.model.FixedExpense
import com.finistro.trackingracks.ui.components.SteampunkCard
import com.finistro.trackingracks.ui.theme.Brass
import com.finistro.trackingracks.ui.theme.NeonRed
import com.finistro.trackingracks.ui.theme.TextSecondary
import com.finistro.trackingracks.viewmodel.GigViewModel
import java.time.LocalDate

@Composable
fun ExpenseScreen(viewModel: GigViewModel) {
    val fixedExpenses by viewModel.fixedExpenses.collectAsState()
    val dailyExpenses by viewModel.dailyExpenses.collectAsState()
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(scrollState)) {
        Text("Expenses", style = MaterialTheme.typography.headlineSmall, color = Brass)
        
        Spacer(Modifier.height(8.dp))

        // Fixed Expenses Section
        Text("Fixed Expenses", color = Brass, style = MaterialTheme.typography.titleSmall)
        SteampunkCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf("Insurance", "Lease/Loan", "Subs").forEach { name ->
                    val expense = fixedExpenses.find { it.name == name }
                    var amount by remember(expense) { mutableStateOf(expense?.amount?.toString() ?: "") }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(name, color = Brass, modifier = Modifier.weight(1f), fontSize = 14.sp)
                        OutlinedTextField(
                            value = amount,
                            onValueChange = {
                                amount = it
                                val doubleAmount = it.toDoubleOrNull() ?: 0.0
                                viewModel.addFixedExpense(
                                    FixedExpense(
                                        id = expense?.id ?: java.util.UUID.randomUUID().toString(),
                                        name = name,
                                        amount = doubleAmount,
                                        frequency = "Monthly"
                                    )
                                )
                            },
                            modifier = Modifier.width(100.dp).height(48.dp),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodySmall,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Brass,
                                unfocusedTextColor = Brass,
                                focusedBorderColor = Brass,
                                unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                            )
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // Daily Expenses Section
        Text("Daily Expenses", color = Brass, style = MaterialTheme.typography.titleSmall)
        SteampunkCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                
                // Establishment Row
                var establishment by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = establishment,
                    onValueChange = { establishment = it },
                    label = { Text("Establishment", color = Brass, fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodySmall,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Brass,
                        unfocusedTextColor = Brass,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    )
                )

                // Gas row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Gas", color = Brass, modifier = Modifier.width(40.dp), fontSize = 14.sp)
                    
                    var cpg by remember { mutableStateOf("") }
                    var totalAmount by remember { mutableStateOf("") }

                    OutlinedTextField(
                        value = cpg,
                        onValueChange = { cpg = it },
                        label = { Text("CPG", color = Brass, fontSize = 10.sp) },
                        modifier = Modifier.weight(1f).height(48.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodySmall,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Brass,
                            unfocusedTextColor = Brass,
                            focusedBorderColor = Brass,
                            unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        )
                    )
                    OutlinedTextField(
                        value = totalAmount,
                        onValueChange = {
                            totalAmount = it
                            val amountValue = it.toDoubleOrNull() ?: 0.0
                            if (amountValue > 0) {
                                viewModel.addDailyExpense(
                                    DailyExpense(
                                        name = "Gas (CPG: $cpg)",
                                        amount = amountValue,
                                        category = "Gas",
                                        establishment = establishment,
                                        date = LocalDate.now().toString()
                                    )
                                )
                            }
                        },
                        label = { Text("Total", color = Brass, fontSize = 10.sp) },
                        modifier = Modifier.weight(1f).height(48.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodySmall,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Brass,
                            unfocusedTextColor = Brass,
                            focusedBorderColor = Brass,
                            unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                        )
                    )
                }

                // Food/Snack row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExpenseInput(label = "Food", establishment = establishment, viewModel = viewModel, dailyExpenses = dailyExpenses, modifier = Modifier.weight(1f))
                    ExpenseInput(label = "Snack", establishment = establishment, viewModel = viewModel, dailyExpenses = dailyExpenses, modifier = Modifier.weight(1f))
                }

                // Unforeseen/Misc row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExpenseInput(label = "Unforeseen", establishment = establishment, viewModel = viewModel, dailyExpenses = dailyExpenses, modifier = Modifier.weight(1f))
                    ExpenseInput(label = "Misc", establishment = establishment, viewModel = viewModel, dailyExpenses = dailyExpenses, modifier = Modifier.weight(1f))
                }

                Spacer(Modifier.height(8.dp))
                Text("Recent Activity", color = Brass, style = MaterialTheme.typography.labelLarge)
                
                // Condensed Recent Activity
                Column(modifier = Modifier.heightIn(max = 200.dp).verticalScroll(rememberScrollState())) {
                    dailyExpenses.sortedByDescending { it.date }.forEach { expense ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("${expense.date} - ${expense.category}", color = Brass, fontSize = 12.sp)
                                if (expense.establishment.isNotBlank()) {
                                    Text(expense.establishment, color = TextSecondary, fontSize = 10.sp)
                                }
                                Text(
                                    "$${"%.2f".format(expense.amount)}",
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                            IconButton(onClick = { viewModel.deleteDailyExpense(expense.id) }, modifier = Modifier.size(24.dp)) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = NeonRed,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseInput(
    label: String,
    establishment: String,
    viewModel: GigViewModel,
    dailyExpenses: List<DailyExpense>,
    modifier: Modifier = Modifier
) {
    val expense = dailyExpenses.find { it.category == label && it.date == LocalDate.now().toString() }
    var amountText by remember(expense) { mutableStateOf(expense?.amount?.toString() ?: "") }

    OutlinedTextField(
        value = amountText,
        onValueChange = {
            amountText = it
            val doubleAmount = it.toDoubleOrNull() ?: 0.0
            viewModel.addDailyExpense(
                DailyExpense(
                    id = expense?.id ?: java.util.UUID.randomUUID().toString(),
                    name = label,
                    amount = doubleAmount,
                    category = label,
                    establishment = establishment,
                    date = LocalDate.now().toString()
                )
            )
        },
        label = { Text(label, color = Brass, fontSize = 10.sp) },
        modifier = modifier.height(48.dp),
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Brass,
            unfocusedTextColor = Brass,
            focusedBorderColor = Brass,
            unfocusedBorderColor = Brass.copy(alpha = 0.5f),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
        )
    )
}
