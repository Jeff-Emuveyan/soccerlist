package com.seamfix.maontestapplication.ui.main

import androidx.lifecycle.ViewModel
import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.data.repositories.CompetitionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class MainViewModel @Inject constructor(val competitionRepository: CompetitionRepository) : ViewModel() {


    open suspend fun getCompetitions(): List<Competition>?{
        var competitions =  competitionRepository.getCompetitionsFromRemoteSource()
        if(competitions != null){//save the competitions:
            saveLocally(competitions)
        }else{
            competitions = competitionRepository.getCompetitionsFromLocalSource()
        }
        return competitions
    }


    open suspend fun saveLocally(competitions: List<Competition>) {
        competitions.forEach { competition ->
                competitionRepository save competition
        }
    }
}