package com.seamfix.maontestapplication.data.repositories

import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.data.model.response.competitions.CompetitionsResponse
import com.seamfix.maontestapplication.data.source.local.AppDatabase
import com.seamfix.maontestapplication.data.source.remote.Service
import javax.inject.Inject

open class CompetitionRepository @Inject constructor(val service: Service, val database: AppDatabase) {

    open suspend fun getCompetitionsFromRemoteSource(): List<Competition>?{
        return try {
            val response = service.getCompetitions()
            if(response.code() == 200  && response.body() !=  null){
                val competitionResponse  = response.body() as CompetitionsResponse
                competitionResponse.competitions
            }else{
               null
            }
        }catch (e: Exception){
            null
        }
    }

    open suspend fun getCompetitionsFromLocalSource(): List<Competition>?{
       return database.competitionDao().getAll()
    }


    suspend infix fun save(newCompetition: Competition){
        val existingCompetition = database.competitionDao().getByID(newCompetition.id)
        if(existingCompetition == null){
            database.competitionDao().save(newCompetition)
        }else{
            database.competitionDao().update(newCompetition)
        }
    }
}