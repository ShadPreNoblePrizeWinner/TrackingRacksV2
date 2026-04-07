package com.finistro.trackingracks.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finistro.trackingracks.viewmodel.GigViewModel
import com.finistro.trackingracks.ui.theme.SteampunkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SteampunkTheme {
                val vm: GigViewModel = viewModel()
                TrackingRacksApp(vm)
            }
        }


    }
}
