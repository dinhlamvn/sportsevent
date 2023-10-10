package com.inspius.sportsevent.repository

import com.inspius.sportsevent.api.Apis
import com.inspius.sportsevent.model.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TeamRepository {

    suspend fun getTeams(): Flow<List<Team>> {
        return flowOf(Apis.services.getTeams().teams.map(Team::from))
    }
}