package com.seamfix.maontestapplication.ui.teams

import androidx.lifecycle.ViewModel
import com.seamfix.maontestapplication.data.model.entity.Team
import com.seamfix.maontestapplication.data.repositories.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class TeamsViewModel @Inject constructor(val teamRepository: TeamRepository): ViewModel() {

    suspend fun getTeams(competitionId: Int): List<Team>?{
        var teams = teamRepository getTeamsFromRemoteSource competitionId
        if(teams != null){//save the teams
            saveLocally(teams)
        }else{
            teams = teamRepository getTeamsFromLocalSource competitionId
        }
        return teams
    }


    open suspend fun saveLocally(teams: List<Team>) {
        teams.forEach {team ->
            teamRepository save team
        }
    }

}