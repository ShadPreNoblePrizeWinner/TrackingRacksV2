package com.finistro.trackingracks.data.db

import com.finistro.trackingracks.data.model.GigEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

// Placeholder - using JSON file storage instead
interface GigDao {
    suspend fun insertGig(gig: GigEntry)
    suspend fun getGigById(id: Int): GigEntry?
    fun getAllGigs(): Flow<List<GigEntry>>
}