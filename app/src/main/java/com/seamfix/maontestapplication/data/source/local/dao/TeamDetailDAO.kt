package com.seamfix.maontestapplication.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.data.model.entity.Team
import com.seamfix.maontestapplication.data.model.entity.TeamDetail

@Dao
interface TeamDetailDAO {

    @Insert
    suspend fun save(teamDetail: TeamDetail)

    @Query("SELECT * FROM team_detail WHERE id = :id LIMIT 1")
    suspend fun getByID(id: Int): TeamDetail?

    @Update
    suspend fun update(teamDetail: TeamDetail)
}