package com.finistro.trackingracks.app

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface
import com.finistro.trackingracks.ui.theme.IronLight
import com.finistro.trackingracks.ui.components.SteampunkBottomNav
import com.finistro.trackingracks.ui.screens.addgig.AddGigScreen
import com.finistro.trackingracks.ui.screens.dashboard.DashboardScreen
import com.finistro.trackingracks.ui.screens.expense.ExpenseScreen
import com.finistro.trackingracks.ui.screens.heatmap.HeatmapScreen
import com.finistro.trackingracks.ui.screens.home.HomeScreen
import com.finistro.trackingracks.viewmodel.GigViewModel

@Composable
fun TrackingRacksApp(viewModel: GigViewModel) {
    var currentScreen by remember { mutableStateOf(Screen.Home) }

    BackHandler(enabled = currentScreen != Screen.Home) {
        currentScreen = Screen.Home
    }

    val density = LocalDensity.current
    val triggerDistancePx = with(density) { 72.dp.toPx() }

    var totalDragX by remember { mutableFloatStateOf(0f) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = IronLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(currentScreen) {
                    detectHorizontalDragGestures(
                        onDragStart = {
                            totalDragX = 0f
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            totalDragX += dragAmount
                            if (totalDragX >= triggerDistancePx && currentScreen != Screen.Home) {
                                currentScreen = Screen.Home
                                totalDragX = 0f
                            }
                            change.consume()
                        },
                        onDragCancel = {
                            totalDragX = 0f
                        },
                        onDragEnd = {
                            totalDragX = 0f
                        }
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                when (currentScreen) {
                    Screen.Home -> HomeScreen(
                        viewModel = viewModel,
                        onAddGig = { currentScreen = Screen.AddGig },
                        onNavigate = { selected ->
                            currentScreen = Screen.valueOf(selected)
                        }
                    )

                    Screen.AddGig -> AddGigScreen(
                        viewModel = viewModel,
                        onDone = { currentScreen = Screen.Home }
                    )

                    Screen.Dashboard -> DashboardScreen(
                        viewModel = viewModel,
                        onGoToPreviousRacks = { currentScreen = Screen.Home },
                        onGoToExpensePage = { currentScreen = Screen.Expense }
                    )

                    Screen.Heatmap -> HeatmapScreen()

                    Screen.Expense -> ExpenseScreen(viewModel = viewModel)
                }
            }

            // Bottom Navigation
            if (currentScreen != Screen.AddGig) {
                SteampunkBottomNav(
                    current = currentScreen.name,
                    onSelect = { selected ->
                        currentScreen = Screen.valueOf(selected)
                    }
                )
            }
        }
    }
}

enum class Screen {
    Home,
    AddGig,
    Dashboard,
    Heatmap,
    Expense
}
