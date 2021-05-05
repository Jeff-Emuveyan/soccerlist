package com.seamfix.maontestapplication.data.repositories

import com.seamfix.maontestapplication.data.model.entity.Team
import com.seamfix.maontestapplication.data.model.entity.TeamDetail
import com.seamfix.maontestapplication.data.model.response.teams.TeamsResponse
import com.seamfix.maontestapplication.data.model.response.teams.details.TeamDetailResponse
import com.seamfix.maontestapplication.data.source.local.AppDatabase
import com.seamfix.maontestapplication.data.source.remote.Service
import java.lang.Exception
import javax.inject.Inject

open class TeamRepository @Inject constructor(val service: Service, val database: AppDatabase) {

    open suspend infix fun getTeamsFromRemoteSource(competitionId: Int): List<Team>?{
        return try {
            val response = service.getTeams(competitionId)
            if(response.code() == 200  && response.body() != null){
                val teamsResponse = response.body() as TeamsResponse
                teamsResponse.teams?.forEach {
                    it.competitionId = competitionId
                }
                teamsResponse.teams
            }else{
                null
            }
        }catch (e: Exception){
            null
        }
    }

    open suspend infix fun getTeamsFromLocalSource(competitionID: Int): List<Team>? =
            database.teamDao().getAll(competitionID)


    suspend infix fun save(newTeam: Team){
        val existingTeam = database.teamDao().getByID(newTeam.id)
        if(existingTeam == null){
            database.teamDao().save(newTeam)
        }else{
            database.teamDao().update(newTeam)
        }
    }


    open suspend infix fun getTeamDetailFromRemoteSource(id: Int): TeamDetail?{
        return try {
            val response = service.getTeamDetails(id)
            if(response.code() == 200 && response.body() != null){
                val teamDetailResponse = response.body() as TeamDetailResponse
                teamDetailResponse.toTeamDetail()
            }else{
                null
            }
        }catch (e: Exception){
            null
        }
    }

    open suspend infix fun getTeamDetailFromLocalSource(id: Int) = database.teamDetailDao().getByID(id)


    suspend infix fun save(newTeamDetail: TeamDetail){
        val existingTeamDetail = database.teamDetailDao().getByID(newTeamDetail.id)
        if(existingTeamDetail == null){
            database.teamDetailDao().save(newTeamDetail)
        }else{
            database.teamDetailDao().update(newTeamDetail)
        }
    }

}