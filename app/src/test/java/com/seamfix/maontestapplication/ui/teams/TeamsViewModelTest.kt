package com.seamfix.maontestapplication.ui.teams

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.seamfix.maontestapplication.data.model.entity.Team
import com.seamfix.maontestapplication.data.repositories.TeamRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.mockito.Mockito

class TeamsViewModelTest {

    var teamRepository: TeamRepository = mock()
    @Test
    fun `getTeams() should return list of teams from remote source`() = runBlocking {

        val teamA = Team(id = 1)
        val teamB = Team(id = 2)
        val teamC = Team(id = 3)
        val successResponse =  listOf(teamA, teamB, teamC)
        Mockito.`when`(teamRepository.getTeamsFromRemoteSource(any())).thenReturn(successResponse)

        var model = TeamsViewModel(teamRepository)
        val spyModel = spy(model)
        doReturn(Unit).`when`(spyModel).saveLocally(any())
        var result = spyModel.getTeams(1)

        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(successResponse)))
    }


    @Test
    fun `getTeams() should return list of teams from local source`() = runBlocking {

        val teamA = Team(id = 1)
        val teamB = Team(id = 2)
        val teamC = Team(id = 3)
        val successResponse =  listOf(teamA, teamB, teamC)
        Mockito.`when`(teamRepository.getTeamsFromRemoteSource(any())).thenReturn(null)
        Mockito.`when`(teamRepository.getTeamsFromLocalSource(any())).thenReturn(successResponse)

        var model = TeamsViewModel(teamRepository)
        val spyModel = spy(model)
        doReturn(Unit).`when`(spyModel).saveLocally(any())
        var result = spyModel.getTeams(1)

        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(successResponse)))
    }

}