package com.finistro.trackingracks.ui.screens.expense

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finistro.trackingracks.data.model.DailyExpense
import com.finistro.trackingracks.data.model.FixedExpense
import com.finistro.trackingracks.ui.components.SteampunkButton
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

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Expenses", style = MaterialTheme.typography.headlineMedium, color = Brass)
        Spacer(Modifier.height(16.dp))

        // Fixed Expenses Section
        Text("Fixed Expenses (Monthly/Recurring)", color = Brass)
        SteampunkCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp)) {
                listOf("Insurance", "Lease/Loan", "Subscriptions").forEach { name ->
                    val expense = fixedExpenses.find { it.name == name }
                    var amount by remember(expense) { mutableStateOf(expense?.amount?.toString() ?: "") }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(name, color = Brass, modifier = Modifier.weight(1f))
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
                            label = { Text("Amount", color = Brass) },
                            modifier = Modifier.width(120.dp),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Brass,
                                unfocusedTextColor = Brass,
                                focusedBorderColor = Brass,
                                unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                                focusedLabelColor = Brass,
                                unfocusedLabelColor = Brass.copy(alpha = 0.7f),
                                cursorColor = Brass,
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                            )
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                    items(fixedExpenses.filter { it.name !in listOf("Insurance", "Lease/Loan", "Subscriptions") }) { expense ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(expense.name, color = Brass)
                                Text(
                                    "$${"%.2f".format(expense.amount)} (${expense.frequency})",
                                    color = TextSecondary
                                )
                            }
                            IconButton(onClick = { viewModel.deleteFixedExpense(expense.id) }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = NeonRed
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Daily Expenses Section
        Text("Daily / Variable Expenses", color = Brass)
        SteampunkCard(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(dailyExpenses.sortedByDescending { it.date }) { expense ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("${expense.date} - ${expense.name}", color = Brass)
                            Text(
                                "${expense.category}: $${"%.2f".format(expense.amount)}",
                                color = TextSecondary
                            )
                        }
                        IconButton(onClick = { viewModel.deleteDailyExpense(expense.id) }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = NeonRed
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddFixedExpenseDialog(onDismiss: () -> Unit, onSave: (String, Double, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf("Monthly") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Fixed Expense", color = Brass) },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    "Select Category or Enter Custom:",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf("Insurance", "Lease", "Payment").forEach { cat ->
                        AssistChip(
                            onClick = { name = cat },
                            label = { Text(cat) },
                            colors = AssistChipDefaults.assistChipColors(labelColor = Brass)
                        )
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name (e.g. Insurance)", color = Brass) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Brass,
                        unfocusedTextColor = Brass,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Brass
                    )
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount", color = Brass) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Brass,
                        unfocusedTextColor = Brass,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Brass
                    )
                )

                Spacer(Modifier.height(8.dp))
                Text("Frequency:", color = Brass)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = frequency == "Monthly",
                        onClick = { frequency = "Monthly" },
                        label = { Text("Monthly") }
                    )
                    FilterChip(
                        selected = frequency == "6 Months",
                        onClick = { frequency = "6 Months" },
                        label = { Text("6 Months") }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { onSave(name, amount.toDoubleOrNull() ?: 0.0, frequency) }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddDailyExpenseDialog(onDismiss: () -> Unit, onSave: (String, Double, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Gas") }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Daily Expense", color = Brass) },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    "Categories:",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
                FlowRow(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)) {
                    listOf("Gas", "Maintenance", "Tolls", "Parking", "Food").forEach { cat ->
                        AssistChip(
                            onClick = { category = cat; if (name.isEmpty()) name = cat },
                            label = { Text(cat) },
                            modifier = Modifier.padding(end = 4.dp),
                            colors = AssistChipDefaults.assistChipColors(labelColor = Brass)
                        )
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Description", color = Brass) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Brass,
                        unfocusedTextColor = Brass,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Brass
                    )
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount", color = Brass) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Brass,
                        unfocusedTextColor = Brass,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Brass
                    )
                )
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category", color = Brass) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Brass,
                        unfocusedTextColor = Brass,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Brass
                    )
                )
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)", color = Brass) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Brass,
                        unfocusedTextColor = Brass,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Brass
                    )
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(name, amount.toDoubleOrNull() ?: 0.0, category, date) }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DummyFlowRowWrapper(content: @Composable () -> Unit) {
    // FlowRow is used above, but it requires OptIn or specific versions.
    // Assuming Material3 FlowRow is available.
}
