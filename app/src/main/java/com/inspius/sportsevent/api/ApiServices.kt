package com.inspius.sportsevent.api

import com.inspius.sportsevent.api.response.MatchesListing
import com.inspius.sportsevent.api.response.TeamListing
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("teams")
    suspend fun getTeams(): TeamListing

    @GET("teams/matches")
    suspend fun getMatches(): MatchesListing

    @GET("teams/{id}/matches")
    suspend fun getMatches(@Path("id") teamId: String): MatchesListing
}