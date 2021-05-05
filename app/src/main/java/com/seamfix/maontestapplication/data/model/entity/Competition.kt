package com.seamfix.maontestapplication.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seamfix.maontestapplication.data.model.common.Area
import com.seamfix.maontestapplication.data.model.response.competitions.CurrentSeason

@Entity(tableName = "competition")
data class Competition(
        val area: Area? = null,
        val lastUpdated: String? = null,
        val emblemUrl: String? = null,
        val currentSeason: CurrentSeason? = null,
        val name: String? = null,
        @PrimaryKey val id: Int,
        val numberOfAvailableSeasons: Int? = null,
        val plan: String? = null
)