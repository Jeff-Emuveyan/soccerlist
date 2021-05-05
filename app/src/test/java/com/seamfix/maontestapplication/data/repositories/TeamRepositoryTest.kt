package com.seamfix.maontestapplication.data.repositories

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.data.model.entity.Team
import com.seamfix.maontestapplication.data.model.response.teams.TeamsResponse
import com.seamfix.maontestapplication.data.model.response.teams.details.TeamDetailResponse
import com.seamfix.maontestapplication.data.source.local.AppDatabase
import com.seamfix.maontestapplication.data.source.local.dao.CompetitionDAO
import com.seamfix.maontestapplication.data.source.local.dao.TeamDAO
import com.seamfix.maontestapplication.data.source.local.dao.TeamDetailDAO
import com.seamfix.maontestapplication.data.source.remote.Service
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class TeamRepositoryTest {

    private val service: Service = mock()
    private val database: AppDatabase = mock()

    @Test
    fun `getTeamsFromRemoteSource() should return the list of teams when service returns data`() =
        runBlocking {

            val teamA = Team(id = 1)
            val teamB = Team(id = 2)
            val teamC = Team(id = 3)
            val successResponse = TeamsResponse(teams = listOf(teamA, teamB, teamC))
            Mockito.`when`(service.getTeams(any())).thenReturn(Response.success(successResponse))

            val repo = TeamRepository(service, database)
            val result = repo.getTeamsFromRemoteSource(1)

            MatcherAssert.assertThat(
                result,
                CoreMatchers.`is`(CoreMatchers.equalTo(successResponse.teams))
            )
        }

    @Test
    fun `getTeamsFromLocalSource() should return the list of teams when database returns data`() =
        runBlocking {

            val teamA = Team(id = 1)
            val teamB = Team(id = 2)
            val teamC = Team(id = 3)

            val teamDAO: TeamDAO = mock()
            Mockito.`when`(teamDAO.getAll(any())).thenReturn(listOf(teamA, teamB, teamC))
            Mockito.`when`(database.teamDao()).thenReturn(teamDAO)


            val repo = TeamRepository(service, database)
            val result = repo.getTeamsFromLocalSource(1)

            MatcherAssert.assertThat(
                result,
                CoreMatchers.`is`(CoreMatchers.equalTo(listOf(teamA, teamB, teamC)))
            )
        }

    @Test
    fun `new data should be saved to database`() = runBlocking {
        val team = Team(id = 1)

        val teamDAO: TeamDAO = mock()
        Mockito.`when`(teamDAO.getByID(any())).thenReturn(null)
        Mockito.`when`(database.teamDao()).thenReturn(teamDAO)

        val repo = TeamRepository(service, database)
        repo.save(team)
        verify(teamDAO).save(any())
    }

    @Test
    fun `existing data should be updated when save() is called`() = runBlocking {
        val team = Team(id = 1)

        val teamDAO: TeamDAO = mock()
        Mockito.`when`(teamDAO.getByID(any())).thenReturn(team)
        Mockito.`when`(database.teamDao()).thenReturn(teamDAO)

        val repo = TeamRepository(service, database)
        repo.save(team)
        verify(teamDAO).update(any())
    }


    @Test
    fun `getTeamDetailFromRemoteSource() should return the list of teams when service returns data`() =
        runBlocking {

            val successResponse = TeamDetailResponse(id = 1)
            Mockito.`when`(service.getTeamDetails(any())).thenReturn(Response.success(successResponse))

            val repo = TeamRepository(service, database)
            val result = repo.getTeamDetailFromRemoteSource(1)

            MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(successResponse.toTeamDetail())))
        }


    @Test
    fun `getTeamDetailFromLocalSource() should return the list of teams when database returns data`() =
        runBlocking {

            val successResponse = TeamDetailResponse(id = 1).toTeamDetail()
            val teamDetailDAO : TeamDetailDAO= mock()
            Mockito.`when`(teamDetailDAO.getByID(any())).thenReturn(successResponse)

            Mockito.`when`(database.teamDetailDao()).thenReturn(teamDetailDAO)

            val repo = TeamRepository(service, database)
            val result = repo.getTeamDetailFromLocalSource(1)

            MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(successResponse)))

        }
}