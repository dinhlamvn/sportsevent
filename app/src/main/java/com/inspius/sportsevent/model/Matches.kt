package com.inspius.sportsevent.model

import com.inspius.sportsevent.api.response.MatchesListing
import com.inspius.sportsevent.base.BaseListAdapter

data class Matches(
    val previous: List<Data>, val upcoming: List<Data>
) {

    data class Data(
        val id: String,
        val date: String,
        val description: String,
        val home: String,
        val away: String,
        val winner: String?,
        val highlights: String?,
        val type: MatchesType,
    ) : BaseListAdapter.BaseListModel() {
        companion object {
            fun from(type: MatchesType, matchesData: MatchesListing.MatchesData): Data {
                val id = "${matchesData.date}-${matchesData.home}-${matchesData.away}"
                return Data(
                    id,
                    matchesData.date,
                    matchesData.description,
                    matchesData.home,
                    matchesData.away,
                    matchesData.winner,
                    matchesData.highlights,
                    type,
                )
            }
        }

        override fun isSame(other: BaseListAdapter.BaseListModel): Boolean {
            return this.id == (other as? Data)?.id
        }

        override fun isContentSame(other: BaseListAdapter.BaseListModel): Boolean {
            return this == other
        }
    }
}