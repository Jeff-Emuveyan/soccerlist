package com.seamfix.maontestapplication.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seamfix.maontestapplication.data.model.common.Area

@Entity(tableName = "team")
data class Team(
        val area: Area? = null,
        val venue: String? = null,
        val website: String? = null,
        val address: String? = null,
        val crestUrl: String? = null,
        val tla: String? = null,
        val founded: Int? = null,
        val lastUpdated: String? = null,
        val clubColors: String? = null,
        val phone: String? = null,
        val name: String? = null,
        @PrimaryKey val id: Int,
        val shortName: String? = null,
        val email: String? = null
){
    var competitionId: Int? = null
}