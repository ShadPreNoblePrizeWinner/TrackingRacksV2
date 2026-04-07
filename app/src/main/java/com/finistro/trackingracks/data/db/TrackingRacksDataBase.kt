package com.finistro.trackingracks.data.db

import android.content.Context
import com.finistro.trackingracks.data.model.GigEntry

// Placeholder - using JSON file storage instead
abstract class TrackingRacksDatabase {
    abstract fun gigDao(): GigDao

    companion object {
        @Volatile
        private var INSTANCE: TrackingRacksDatabase? = null

        fun getDatabase(context: Context): TrackingRacksDatabase {
            // Not used - JSON file storage is used instead
            throw NotImplementedError("Use GigRepository instead")
        }
    }
}