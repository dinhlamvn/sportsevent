package com.inspius.sportsevent.data

import com.inspius.sportsevent.model.Matches
import com.inspius.sportsevent.model.MatchesType
import com.inspius.sportsevent.model.Team

fun getTeams(): List<Team> {
    return listOf(
        Team("1", "Team1", "https://abc.com/logo1.jpg"),
        Team("2", "Team2", "https://abc.com/logo2.jpg")
    )
}

fun getMatches(): Matches {
    return Matches(
        listOf(
            Matches.Data(
                "match1",
                "2022-04-23T18:00:00.000Z",
                "Team Cool Eagles vs. Team Red Dragons",
                "Team Cool Eagles",
                "Team Red Dragons",
                "Team Red Dragons",
                "https://tstzj.s3.amazonaws.com/highlights.mp4",
                MatchesType.PREVIOUS
            ), Matches.Data(
                "match2",
                "22-04-24T18:00:00.000Z",
                "Team Chill Elephants vs. Team Royal Knights",
                "Team Chill Elephants",
                "Team Royal Knights",
                "Team Chill Elephants",
                "https://tstzj.s3.amazonaws.com/highlights.mp4",
                MatchesType.PREVIOUS
            )
        ), listOf(
            Matches.Data(
                "match1",
                "2024-04-23T18:00:00.000Z",
                "Team Cool Eagles vs. Team Serious Lions",
                "Team Cool Eagles",
                "Team Serious Lions",
                null,
                null,
                MatchesType.UPCOMING
            ), Matches.Data(
                "match2",
                "2024-04-24T18:00:00.000Z",
                "Team Chill Elephants vs. Team Royal Knights",
                "Team Chill Elephants",
                "Team Royal Knights",
                null,
                null,
                MatchesType.UPCOMING
            )
        )
    )
}

fun getMatchesTeam1(): Matches {
    return Matches(
        listOf(
            Matches.Data(
                "match1",
                "2022-04-23T18:00:00.000Z",
                "Team Cool Eagles vs. Team Red Dragons",
                "Team Cool Eagles",
                "Team Red Dragons",
                "Team Red Dragons",
                "https://tstzj.s3.amazonaws.com/highlights.mp4",
                MatchesType.PREVIOUS
            ), Matches.Data(
                "match2",
                "22-04-24T18:00:00.000Z",
                "Team Cool Eagles vs. Team Royal Knights",
                "Team Cool Eagles",
                "Team Royal Knights",
                "Team Chill Elephants",
                "https://tstzj.s3.amazonaws.com/highlights.mp4",
                MatchesType.PREVIOUS
            )
        ), listOf(
            Matches.Data(
                "match1",
                "2024-04-23T18:00:00.000Z",
                "Team Cool Eagles vs. Team Hungry Sharks",
                "Team Cool Eagles",
                "Team Hungry Sharks",
                null,
                null,
                MatchesType.UPCOMING
            ), Matches.Data(
                "match2",
                "2024-04-24T18:00:00.000Z",
                "Team Cool Eagles vs. Team Growling Tigers",
                "Team Cool Eagles",
                "Team Growling Tigers",
                null,
                null,
                MatchesType.UPCOMING
            )
        )
    )
}

