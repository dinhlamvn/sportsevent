package com.inspius.sportsevent.repository

import com.inspius.sportsevent.api.Apis
import com.inspius.sportsevent.model.Matches
import com.inspius.sportsevent.model.MatchesType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MatchesRepository {

    suspend fun getMatches(teamId: String?): Flow<Matches> {
        val matches = teamId?.let { id ->
            Apis.services.getMatches(id)
        } ?: Apis.services.getMatches()
        val previous = matches.matches.previousMatches.map { matchesData ->
            Matches.Data.from(MatchesType.PREVIOUS, matchesData)
        }
        val upcoming = matches.matches.upcomingMatches.map { matchesData ->
            Matches.Data.from(MatchesType.UPCOMING, matchesData)
        }
        return flowOf(Matches(previous, upcoming))
    }
}