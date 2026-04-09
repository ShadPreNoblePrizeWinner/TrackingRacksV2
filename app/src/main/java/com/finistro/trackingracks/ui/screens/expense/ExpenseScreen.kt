package com.finistro.trackingracks.ui.screens.expense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ExpenseScreen(viewModel: GigViewModel) {
    val fixedExpenses by viewModel.fixedExpenses.collectAsState()
    val dailyExpenses by viewModel.dailyExpenses.collectAsState()
    val scrollState = rememberScrollState()

    val labelColor = LabelBlue
    val inputBg = InputBg
    val inputTextColor = Color.Black
    
    var establishment by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Meal") }
    var categoryExpanded by remember { mutableStateOf(false) }
    val categories = listOf("Meal", "Snack", "Beverages", "Unforeseen", "Vices", "Misc")
    var city by remember { mutableStateOf("") }
    var pricePerUnit by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(InputBg)
        .padding(10.dp)
        .verticalScroll(scrollState)) {
        Text("Expenses", style = MaterialTheme.typography.headlineSmall, color = Brass, fontSize = 26.sp)
        
        Spacer(Modifier.height(8.dp))

        // 1. Daily Expenses Section (Now First)
        Text("Daily Expenses", color = labelColor, style = MaterialTheme.typography.titleSmall, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        SteampunkCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                
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
                        .background(ContainerBg, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = pricePerUnit,
                            onValueChange = { pricePerUnit = it },
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
                        ExpenseInput(
                            label = "Total",
                            establishment = establishment,
                            viewModel = viewModel,
                            dailyExpenses = dailyExpenses,
                            labelColor = labelColor,
                            inputBg = inputBg,
                            inputTextColor = inputTextColor,
                            modifier = Modifier.weight(1f),
                            customCategory = "Gas",
                            customName = "Gas (CPG: $pricePerUnit)"
                        )
                    }
                }

                // Food Dropdown & Input Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Category", color = labelColor, fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                            trailingIcon = {
                                Icon(Icons.Default.ArrowDropDown, "Drop Down", tint = Brass, modifier = Modifier.clickable { categoryExpanded = true })
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = inputTextColor,
                                unfocusedTextColor = inputTextColor,
                                focusedBorderColor = Brass,
                                unfocusedBorderColor = Brass.copy(alpha = 0.5f),
                                focusedContainerColor = inputBg,
                                unfocusedContainerColor = inputBg,
                            )
                        )
                        DropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false }
                        ) {
                            categories.forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category) },
                                    onClick = {
                                        selectedCategory = category
                                        categoryExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    ExpenseInput(
                        label = selectedCategory,
                        establishment = establishment,
                        viewModel = viewModel,
                        dailyExpenses = dailyExpenses,
                        labelColor = labelColor,
                        inputBg = inputBg,
                        inputTextColor = inputTextColor,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Establishment Row
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

                // City Row
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City", color = labelColor, fontSize = 14.sp) },
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
        Text("Fixed Expenses", color = labelColor, style = MaterialTheme.typography.titleSmall, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        SteampunkCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Insurance and Lease/Loan Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Insurance
                    val insuranceExp = fixedExpenses.find { it.name == "Insurance" }
                    var insuranceAmount by remember(insuranceExp) { mutableStateOf(insuranceExp?.amount?.toString() ?: "") }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Insurance", color = labelColor, fontSize = 14.sp)
                        OutlinedTextField(
                            value = insuranceAmount,
                            onValueChange = {
                                insuranceAmount = it
                                val doubleAmount = it.toDoubleOrNull() ?: 0.0
                                viewModel.addFixedExpense(
                                    FixedExpense(
                                        id = insuranceExp?.id ?: java.util.UUID.randomUUID().toString(),
                                        name = "Insurance",
                                        amount = doubleAmount,
                                        frequency = "Monthly"
                                    )
                                )
                            },
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

                    // Lease/Loan
                    val loanExp = fixedExpenses.find { it.name == "Lease/Loan" }
                    var loanAmount by remember(loanExp) { mutableStateOf(loanExp?.amount?.toString() ?: "") }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Loan", color = labelColor, fontSize = 14.sp)
                        OutlinedTextField(
                            value = loanAmount,
                            onValueChange = {
                                loanAmount = it
                                val doubleAmount = it.toDoubleOrNull() ?: 0.0
                                viewModel.addFixedExpense(
                                    FixedExpense(
                                        id = loanExp?.id ?: java.util.UUID.randomUUID().toString(),
                                        name = "Lease/Loan",
                                        amount = doubleAmount,
                                        frequency = "Monthly"
                                    )
                                )
                            },
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

                // Subscriptions Row
                val subsExp = fixedExpenses.find { it.name == "Subscriptions" }
                var subsAmount by remember(subsExp) { mutableStateOf(subsExp?.amount?.toString() ?: "") }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Subscriptions", color = labelColor, modifier = Modifier.weight(1f), fontSize = 16.sp)
                    OutlinedTextField(
                        value = subsAmount,
                        onValueChange = {
                            subsAmount = it
                            val doubleAmount = it.toDoubleOrNull() ?: 0.0
                            viewModel.addFixedExpense(
                                FixedExpense(
                                    id = subsExp?.id ?: java.util.UUID.randomUUID().toString(),
                                    name = "Subscriptions",
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

        Spacer(Modifier.height(12.dp))

        // 3. Rolling Transactions (Below Fixed Expenses)
        Text("Rolling Transactions (Last 5)", color = labelColor, style = MaterialTheme.typography.titleSmall, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        
        SteampunkCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(10.dp)) {
                if (dailyExpenses.isEmpty()) {
                    Text("No transactions yet", color = TextSecondary, modifier = Modifier.padding(10.dp))
                }
                dailyExpenses.sortedByDescending { it.date }.take(5).forEach { expense ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val parsedDate = try { LocalDate.parse(expense.date) } catch (e: Exception) { LocalDate.now() }
                        val dayName = parsedDate.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.getDefault())
                        val dateShort = parsedDate.format(DateTimeFormatter.ofPattern("MM/dd"))
                        
                        Text(
                            text = "$dayName, $dateShort ${expense.establishment.ifBlank { expense.category }}",
                            color = labelColor,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                        
                        Text(
                            text = "$${"%.2f".format(expense.amount)}",
                            color = Brass,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
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
    modifier: Modifier = Modifier,
    customCategory: String? = null,
    customName: String? = null
) {
    val categoryToFind = customCategory ?: label
    val expense = dailyExpenses.find { it.category == categoryToFind && it.date == LocalDate.now().toString() }
    var amountText by remember(expense) { mutableStateOf(expense?.amount?.toString() ?: "") }

    OutlinedTextField(
        value = amountText,
        onValueChange = {
            amountText = it
            val doubleAmount = it.toDoubleOrNull() ?: 0.0
            viewModel.addDailyExpense(
                DailyExpense(
                    id = expense?.id ?: java.util.UUID.randomUUID().toString(),
                    name = customName ?: label,
                    amount = doubleAmount,
                    category = categoryToFind,
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
