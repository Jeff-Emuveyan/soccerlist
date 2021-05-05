package com.seamfix.maontestapplication.ui.teams.detail

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.seamfix.maontestapplication.data.model.entity.TeamDetail
import com.seamfix.maontestapplication.data.repositories.TeamRepository
import com.seamfix.maontestapplication.ui.teams.TeamsViewModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.mockito.Mockito

class TeamDetailViewModelTest {

    var teamRepository: TeamRepository = mock()

    @Test
    fun `getTeamDetail() should return list of teamsDetails from remote source`() = runBlocking {

        val successResponse = TeamDetail(id = 1)
        Mockito.`when`(teamRepository.getTeamDetailFromRemoteSource(any())).thenReturn(successResponse)

        var model = TeamDetailViewModel(teamRepository)
        val spyModel = spy(model)
        doReturn(Unit).`when`(spyModel).saveLocally(any())
        var result = spyModel.getTeamDetail(1)

        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(successResponse)))

    }

    @Test
    fun `getTeamDetail() should return list of teamsDetails from local source`() = runBlocking {

        val successResponse = TeamDetail(id = 1)
        Mockito.`when`(teamRepository.getTeamDetailFromRemoteSource(any())).thenReturn(null)
        Mockito.`when`(teamRepository.getTeamDetailFromLocalSource(any())).thenReturn(successResponse)

        var model = TeamDetailViewModel(teamRepository)
        val spyModel = spy(model)
        doReturn(Unit).`when`(spyModel).saveLocally(any())
        var result = spyModel.getTeamDetail(1)

        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(successResponse)))

    }
}