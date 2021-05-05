package com.seamfix.maontestapplication.util.hilt

import com.seamfix.maontestapplication.data.model.response.competitions.CompetitionsResponse
import com.seamfix.maontestapplication.data.model.response.teams.TeamsResponse
import com.seamfix.maontestapplication.data.model.response.teams.details.TeamDetailResponse
import com.seamfix.maontestapplication.data.source.remote.Service
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject


class ServiceImpl @Inject constructor(var retrofit: Retrofit): Service {

    override suspend fun getCompetitions(): Response<CompetitionsResponse> {
        return retrofit.create(Service::class.java).getCompetitions()
    }

    override suspend fun getTeams(competitionId: Int): Response<TeamsResponse> {
        return retrofit.create(Service::class.java).getTeams(competitionId)
    }

    override suspend fun getTeamDetails(id: Int): Response<TeamDetailResponse> {
        return retrofit.create(Service::class.java).getTeamDetails(id)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class SServiceModule {
    @Binds
    abstract fun bindService(serviceImpl: ServiceImpl): Service
}