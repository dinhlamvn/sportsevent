package com.inspius.sportsevent.model

import com.inspius.sportsevent.api.response.TeamListing
import com.inspius.sportsevent.base.BaseListAdapter

data class Team(
    val id: String, val name: String, val logo: String
) : BaseListAdapter.BaseListModel() {

    companion object {
        fun from(teamData: TeamListing.TeamData): Team {
            return Team(teamData.id, teamData.name, teamData.logo)
        }
    }

    override fun isSame(other: BaseListAdapter.BaseListModel): Boolean {
        return id == (other as? Team)?.id
    }

    override fun isContentSame(other: BaseListAdapter.BaseListModel): Boolean {
        return this == other
    }
}
