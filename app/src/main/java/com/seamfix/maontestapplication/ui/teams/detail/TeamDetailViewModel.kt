package com.seamfix.maontestapplication.ui.teams.detail

import androidx.lifecycle.ViewModel
import com.seamfix.maontestapplication.data.model.entity.TeamDetail
import com.seamfix.maontestapplication.data.repositories.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class TeamDetailViewModel @Inject constructor(val teamRepository: TeamRepository): ViewModel() {

    suspend fun getTeamDetail(teamId: Int): TeamDetail?{
        var teamDetail = teamRepository getTeamDetailFromRemoteSource teamId
        if(teamDetail != null){
            saveLocally(teamDetail)
        }else{
            teamDetail = teamRepository getTeamDetailFromLocalSource teamId
        }
        return teamDetail
    }

    open suspend fun saveLocally(teamDetail: TeamDetail) {
        teamRepository save teamDetail
    }
}