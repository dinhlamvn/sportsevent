package com.inspius.sportsevent.api.response

import com.google.gson.annotations.SerializedName

data class MatchesListing(
    @SerializedName("matches") val matches: Matches
) {

    data class Matches(
        @SerializedName("previous") val previousMatches: List<MatchesData>,
        @SerializedName("upcoming") val upcomingMatches: List<MatchesData>
    )

    data class MatchesData(
        @SerializedName("date") val date: String,
        @SerializedName("description") val description: String,
        @SerializedName("home") val home: String,
        @SerializedName("away") val away: String,
        @SerializedName("winner") val winner: String?,
        @SerializedName("highlights") val highlights: String?,
    )
}
