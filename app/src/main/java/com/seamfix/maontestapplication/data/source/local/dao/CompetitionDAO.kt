package com.seamfix.maontestapplication.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.seamfix.maontestapplication.data.model.entity.Competition

@Dao
interface CompetitionDAO {

    @Insert
    suspend fun save(competition: Competition)

    @Query("SELECT * FROM competition WHERE id = :id LIMIT 1")
    suspend fun getByID(id: Int): Competition?

    @Query("SELECT * FROM competition")
    suspend fun getAll(): List<Competition>?

    @Update
    suspend fun update(competition: Competition)
}