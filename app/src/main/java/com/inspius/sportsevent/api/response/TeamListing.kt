package com.inspius.sportsevent.api.response

import com.google.gson.annotations.SerializedName

data class TeamListing(
    @SerializedName("teams")
    val teams: List<TeamData>
) {

    data class TeamData(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("logo")
        val logo: String
    )
}
