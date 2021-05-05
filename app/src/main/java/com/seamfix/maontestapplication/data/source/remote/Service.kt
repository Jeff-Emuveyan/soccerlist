package com.seamfix.maontestapplication.data.source.remote
import com.seamfix.maontestapplication.data.model.response.teams.TeamsResponse
import com.seamfix.maontestapplication.data.model.response.competitions.CompetitionsResponse
import com.seamfix.maontestapplication.data.model.response.teams.details.TeamDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("competitions")
    suspend fun getCompetitions(): Response<CompetitionsResponse>

    @GET("competitions/{competitionId}/teams")
    suspend fun getTeams(@Path("competitionId")  competitionId: Int): Response<TeamsResponse>

    @GET("teams/{id}")
    suspend fun getTeamDetails(@Path("id")  id: Int): Response<TeamDetailResponse>
}