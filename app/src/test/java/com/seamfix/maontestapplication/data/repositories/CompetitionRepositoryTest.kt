package com.seamfix.maontestapplication.data.repositories

import android.content.Context
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.seamfix.maontestapplication.data.model.entity.Competition
import com.seamfix.maontestapplication.data.model.response.competitions.CompetitionsResponse
import com.seamfix.maontestapplication.data.source.local.AppDatabase
import com.seamfix.maontestapplication.data.source.local.dao.CompetitionDAO
import com.seamfix.maontestapplication.data.source.remote.Service
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class CompetitionRepositoryTest {

    private val service: Service = mock()
    private val database: AppDatabase = mock()

    @Test
    fun `getCompetitionsFromRemoteSource() should return competition list when service returns data`() = runBlocking{

        val competitionA = Competition(id = 1)
        val competitionB = Competition(id = 2)
        val competitionC = Competition(id = 3)
        val successResponse = CompetitionsResponse(competitions = listOf(competitionA, competitionB, competitionC))
        Mockito.`when`(service.getCompetitions()).thenReturn(Response.success(successResponse))

        val repo = CompetitionRepository(service, database)
        val result = repo.getCompetitionsFromRemoteSource()

        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(successResponse.competitions)))

    }


    @Test
    fun `getCompetitionsFromRemoteSource() should return null when service does not return data`() = runBlocking{

        Mockito.`when`(service.getCompetitions()).thenReturn(null)

        val repo = CompetitionRepository(service, database)
        val result = repo.getCompetitionsFromRemoteSource()

        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(null)))

    }

    @Test
    fun `getCompetitionsFromLocalSource() should return data if the database has this data`() = runBlocking {

        val competitionA = Competition(id = 1)
        val competitionB = Competition(id = 2)
        val competitionC = Competition(id = 3)
        val list = listOf(competitionA, competitionB, competitionC)

        val competitionDao: CompetitionDAO = mock()
        Mockito.`when`(competitionDao.getAll()).thenReturn(list)
        Mockito.`when`(database.competitionDao()).thenReturn(competitionDao)

        val repo = CompetitionRepository(service, database)
        val result = repo.getCompetitionsFromLocalSource()
        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(list)))

    }

    @Test
    fun `getCompetitionsFromLocalSource() should return null if the database has no data`() = runBlocking {

        val competitionDao: CompetitionDAO = mock()
        Mockito.`when`(competitionDao.getAll()).thenReturn(null)
        Mockito.`when`(database.competitionDao()).thenReturn(competitionDao)

        val repo = CompetitionRepository(service, database)
        val result = repo.getCompetitionsFromLocalSource()
        MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.equalTo(null)))

    }


    @Test
    fun `new data should be saved to database`() = runBlocking{

        val competition = Competition(id = 1)

        val competitionDao: CompetitionDAO = mock()
        Mockito.`when`(competitionDao.getByID(1)).thenReturn(null)
        Mockito.`when`(database.competitionDao()).thenReturn(competitionDao)

        val repo = CompetitionRepository(service, database)
        repo.save(competition)
        verify(competitionDao).save(any())
    }

    @Test
    fun `existing data should be updated when save() is  called`() = runBlocking{

        val competition = Competition(id = 1)

        val competitionDao: CompetitionDAO = mock()
        Mockito.`when`(competitionDao.getByID(1)).thenReturn(competition)
        Mockito.`when`(database.competitionDao()).thenReturn(competitionDao)

        val repo = CompetitionRepository(service, database)
        repo.save(competition)
        verify(competitionDao).update(any())
    }
}