package com.finistro.trackingracks.ui.screens.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finistro.trackingracks.data.model.DailyExpense
import com.finistro.trackingracks.data.model.FixedExpense
import com.finistro.trackingracks.ui.components.SteampunkCard
import com.finistro.trackingracks.ui.theme.*
import com.finistro.trackingracks.viewmodel.GigViewModel
import java.time.LocalDate

@Composable
fun ExpenseScreen(viewModel: GigViewModel) {
    val fixedExpenses by viewModel.fixedExpenses.collectAsState()
    val dailyExpenses by viewModel.dailyExpenses.collectAsState()
    val scrollState = rememberScrollState()

    val labelColor = LabelBlue
    val inputBg = InputBg
    val inputTextColor = Color.Black
    
    var establishment by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(InputBg)
        .padding(16.dp)
        .verticalScroll(scrollState)) {
        Text("Expenses", style = MaterialTheme.typography.headlineSmall, color = Brass, fontSize = 26.sp)
        
        Spacer(Modifier.height(8.dp))

        // 1. Daily Expenses Section (Now First)
        Text("Daily Expenses", color = labelColor, style = MaterialTheme.typography.titleSmall, fontSize = 16.sp)
        SteampunkCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                
                // Gas Header Centered
                Text(
                    text = "Gas",
                    color = labelColor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                // Gas Row with border around CPG and Total
                Box(
                    modifier = Modifier
                        .border(1.dp, Brass.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var cpg by remember { mutableStateOf("") }
                        var totalAmount by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = cpg,
                            onValueChange = { cpg = it },
                            label = { Text("CPG", color = labelColor, fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).height(IntrinsicSize.Min),
                            singleLine = true,
                            textStyle = TextStyle(color = inputTextColor, fontSize = 14.sp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = inputTextColor,
                                unfocusedTextColor = inputTextColor,
                                focusedBorderColor = Brass,
                                unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                                focusedContainerColor = inputBg,
                                unfocusedContainerColor = inputBg,
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
                            label = { Text("Total", color = labelColor, fontSize = 12.sp) },
                            modifier = Modifier.weight(1f).height(IntrinsicSize.Min),
                            singleLine = true,
                            textStyle = TextStyle(color = inputTextColor, fontSize = 14.sp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = inputTextColor,
                                unfocusedTextColor = inputTextColor,
                                focusedBorderColor = Brass,
                                unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                                focusedContainerColor = inputBg,
                                unfocusedContainerColor = inputBg,
                            )
                        )
                    }
                }

                // Food/Snack row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExpenseInput(label = "Food", establishment = establishment, viewModel = viewModel, dailyExpenses = dailyExpenses, labelColor = labelColor, inputBg = inputBg, inputTextColor = inputTextColor, modifier = Modifier.weight(1f))
                    ExpenseInput(label = "Snack", establishment = establishment, viewModel = viewModel, dailyExpenses = dailyExpenses, labelColor = labelColor, inputBg = inputBg, inputTextColor = inputTextColor, modifier = Modifier.weight(1f))
                }

                // Unforeseen/Misc row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ExpenseInput(label = "Unforeseen", establishment = establishment, viewModel = viewModel, dailyExpenses = dailyExpenses, labelColor = labelColor, inputBg = inputBg, inputTextColor = inputTextColor, modifier = Modifier.weight(1f))
                    ExpenseInput(label = "Misc", establishment = establishment, viewModel = viewModel, dailyExpenses = dailyExpenses, labelColor = labelColor, inputBg = inputBg, inputTextColor = inputTextColor, modifier = Modifier.weight(1f))
                }

                // Establishment Row (Now at the bottom of the section)
                OutlinedTextField(
                    value = establishment,
                    onValueChange = { establishment = it },
                    label = { Text("Establishment", color = labelColor, fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                    singleLine = true,
                    textStyle = TextStyle(color = inputTextColor, fontSize = 14.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = inputTextColor,
                        unfocusedTextColor = inputTextColor,
                        focusedBorderColor = Brass,
                        unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                        focusedContainerColor = inputBg,
                        unfocusedContainerColor = inputBg,
                    )
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // 2. Fixed Expenses Section (Now Second)
        Text("Fixed Expenses", color = labelColor, style = MaterialTheme.typography.titleSmall, fontSize = 16.sp)
        SteampunkCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf("Insurance", "Lease/Loan", "Subs").forEach { name ->
                    val expense = fixedExpenses.find { it.name == name }
                    var amount by remember(expense) { mutableStateOf(expense?.amount?.toString() ?: "") }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(name, color = labelColor, modifier = Modifier.weight(1f), fontSize = 16.sp)
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
                            modifier = Modifier.width(100.dp).height(IntrinsicSize.Min),
                            singleLine = true,
                            textStyle = TextStyle(color = inputTextColor, fontSize = 14.sp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = inputTextColor,
                                unfocusedTextColor = inputTextColor,
                                focusedBorderColor = Brass,
                                unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                                focusedContainerColor = inputBg,
                                unfocusedContainerColor = inputBg,
                            )
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        // 3. Rolling Transactions (Below Fixed Expenses)
        Text("Rolling Transactions (Last 5)", color = labelColor, style = MaterialTheme.typography.titleSmall, fontSize = 16.sp)
        
        SteampunkCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(8.dp)) {
                if (dailyExpenses.isEmpty()) {
                    Text("No transactions yet", color = TextSecondary, modifier = Modifier.padding(8.dp))
                }
                dailyExpenses.sortedByDescending { it.date }.take(5).forEach { expense ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("${expense.date} - ${expense.category}", color = labelColor, fontSize = 14.sp)
                            if (expense.establishment.isNotBlank()) {
                                Text(expense.establishment, color = TextSecondary, fontSize = 12.sp)
                            }
                            Text(
                                "$${"%.2f".format(expense.amount)}",
                                color = TextDark,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
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
                    HorizontalDivider(color = Brass.copy(alpha = 0.2f), thickness = 0.5.dp)
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
    labelColor: Color,
    inputBg: Color,
    inputTextColor: Color,
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
        label = { Text(label, color = labelColor, fontSize = 12.sp) },
        modifier = modifier.height(IntrinsicSize.Min),
        singleLine = true,
        textStyle = TextStyle(color = inputTextColor, fontSize = 14.sp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = inputTextColor,
            unfocusedTextColor = inputTextColor,
            focusedBorderColor = Brass,
            unfocusedBorderColor = Brass.copy(alpha = 0.5f),
            focusedContainerColor = inputBg,
            unfocusedContainerColor = inputBg,
        )
    )
}
