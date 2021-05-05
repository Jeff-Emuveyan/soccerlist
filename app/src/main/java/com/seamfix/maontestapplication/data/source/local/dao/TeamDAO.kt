package com.seamfix.maontestapplication.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.data.model.entity.Team

@Dao
interface TeamDAO {

    @Insert
    suspend fun save(team: Team)

    @Query("SELECT * FROM team WHERE id = :id LIMIT 1")
    suspend fun getByID(id: Int): Team?

    @Query("SELECT * FROM team WHERE competitionId = :competitionId")
    suspend fun getAll(competitionId: Int): List<Team>?

    @Update
    suspend fun update(team: Team)
}