package com.seamfix.maontestapplication.ui.main

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.data.repositories.CompetitionRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest{

    val competitionRepository: CompetitionRepository = mock()

    @Test
    fun `getCompetitions() should return list of competitions from remote source`() = runBlocking{

        val competitionA = Competition(id = 1)
        val competitionB = Competition(id = 2)
        val competitionC = Competition(id = 3)
        val successResponse =  listOf(competitionA, competitionB, competitionC)
        Mockito.`when`(competitionRepository.getCompetitionsFromRemoteSource()).thenReturn(successResponse)

        var model = MainViewModel(competitionRepository)
        val spyModel = spy(model)
        doReturn(Unit).`when`(spyModel).saveLocally(any())
        var result = spyModel.getCompetitions()

        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(successResponse)))

    }

    @Test
    fun `getCompetitions() should return list of competitions from local source`() = runBlocking{

        val competitionA = Competition(id = 1)
        val competitionB = Competition(id = 2)
        val competitionC = Competition(id = 3)
        val successResponse =  listOf(competitionA, competitionB, competitionC)
        Mockito.`when`(competitionRepository.getCompetitionsFromRemoteSource()).thenReturn(null)
        Mockito.`when`(competitionRepository.getCompetitionsFromLocalSource()).thenReturn(successResponse)


        var model = MainViewModel(competitionRepository)
        val spyModel = spy(model)
        doReturn(Unit).`when`(spyModel).saveLocally(any())
        var result = spyModel.getCompetitions()

        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(successResponse)))

    }
}
