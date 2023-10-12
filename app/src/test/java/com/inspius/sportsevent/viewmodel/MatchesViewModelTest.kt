package com.inspius.sportsevent.viewmodel

import com.inspius.sportsevent.data.getMatches
import com.inspius.sportsevent.data.getMatchesTeam1
import com.inspius.sportsevent.model.MatchesType
import com.inspius.sportsevent.repository.MatchesRepository
import com.inspius.sportsevent.ui.matches.MatchesUiState
import com.inspius.sportsevent.ui.matches.MatchesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class MatchesViewModelTest {

    @Mock
    lateinit var matchesRepository: MatchesRepository

    private lateinit var viewModel: MatchesViewModel

    @Test
    @ExperimentalCoroutinesApi
    fun matchesViewModel_testGetMatchesPrevious() = runTest {
        val teamId: String? = null
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val matches = getMatches()
        `when`(matchesRepository.getMatches(teamId)).thenReturn(flowOf(matches))

        try {
            viewModel = MatchesViewModel(teamId, matchesRepository)
            viewModel.getMatchesTest(MatchesType.PREVIOUS)
            val fetchedMatches =
                (viewModel.uiState.value as? MatchesUiState.Success)?.data?.previous
            assertEquals(matches.previous, fetchedMatches)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun matchesViewModel_testGetMatchesUpcoming() = runTest {
        val teamId: String? = null
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val matches = getMatches()
        `when`(matchesRepository.getMatches(teamId)).thenReturn(flowOf(matches))

        try {
            viewModel = MatchesViewModel(teamId, matchesRepository)
            viewModel.getMatchesTest(MatchesType.UPCOMING)
            val fetchedMatches =
                (viewModel.uiState.value as? MatchesUiState.Success)?.data?.upcoming
            assertEquals(matches.upcoming, fetchedMatches)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun matchesViewModel_testGetMatchesPrevious_team1() = runTest {
        val teamId = "Team1"
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val matches = getMatchesTeam1()
        `when`(matchesRepository.getMatches(teamId)).thenReturn(flowOf(matches))

        try {
            viewModel = MatchesViewModel(teamId, matchesRepository)
            viewModel.getMatchesTest(MatchesType.PREVIOUS)
            val fetchedMatches =
                (viewModel.uiState.value as? MatchesUiState.Success)?.data?.previous
            assertEquals(matches.previous, fetchedMatches)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun matchesViewModel_testGetMatchesUpcoming_team1() = runTest {
        val teamId = "Team1"
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val matches = getMatchesTeam1()
        `when`(matchesRepository.getMatches(teamId)).thenReturn(flowOf(matches))

        try {
            viewModel = MatchesViewModel(teamId, matchesRepository)
            viewModel.getMatchesTest(MatchesType.UPCOMING)
            val fetchedMatches =
                (viewModel.uiState.value as? MatchesUiState.Success)?.data?.upcoming
            assertEquals(matches.upcoming, fetchedMatches)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun matchesViewModel_testUpdateMatchesType_onSuccess() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val matches = getMatches()
        `when`(matchesRepository.getMatches(null)).thenReturn(flowOf(matches))

        try {
            viewModel = MatchesViewModel(null, matchesRepository)
            viewModel.getMatchesTest(MatchesType.PREVIOUS)

            val currentMatchesType = viewModel.uiState.value.matchesType

            viewModel.updateMatchesType(MatchesType.UPCOMING)

            val updatedMatchesType = viewModel.uiState.value.matchesType
            val fetched = (viewModel.uiState.value as? MatchesUiState.Success)?.data

            assertEquals(true, currentMatchesType != updatedMatchesType)
            assertEquals(matches, fetched)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun matchesViewModel_testUpdateMatchesType_onNoSuccess() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val matches = getMatches()
        `when`(matchesRepository.getMatches(null)).thenReturn(flowOf(matches))

        try {
            viewModel = MatchesViewModel(null, matchesRepository)
            val currentMatchesType = viewModel.uiState.value.matchesType

            viewModel.updateMatchesType(MatchesType.UPCOMING)

            val updatedMatchesType = viewModel.uiState.value.matchesType
            val fetched = (viewModel.uiState.value as? MatchesUiState.Success)?.data

            assertEquals(true, currentMatchesType != updatedMatchesType)
            assertEquals(matches, fetched)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun matchesViewModel_testUpdateMatchesTypeWithTeam_onSuccess() = runTest {
        val teamId = "Team1"
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val matches = getMatchesTeam1()
        `when`(matchesRepository.getMatches(teamId)).thenReturn(flowOf(matches))

        try {
            viewModel = MatchesViewModel(teamId, matchesRepository)
            viewModel.getMatchesTest(MatchesType.PREVIOUS)

            val currentMatchesType = viewModel.uiState.value.matchesType

            viewModel.updateMatchesType(MatchesType.UPCOMING)

            val updatedMatchesType = viewModel.uiState.value.matchesType
            val fetched = (viewModel.uiState.value as? MatchesUiState.Success)?.data

            assertEquals(true, currentMatchesType != updatedMatchesType)
            assertEquals(matches, fetched)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun matchesViewModel_testUpdateMatchesTypeWithTeam_onNoSuccess() = runTest {
        val teamId = "Team1"
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val matches = getMatchesTeam1()
        `when`(matchesRepository.getMatches(teamId)).thenReturn(flowOf(matches))

        try {
            viewModel = MatchesViewModel(teamId, matchesRepository)
            val currentMatchesType = viewModel.uiState.value.matchesType

            viewModel.updateMatchesType(MatchesType.UPCOMING)

            val updatedMatchesType = viewModel.uiState.value.matchesType
            val fetched = (viewModel.uiState.value as? MatchesUiState.Success)?.data

            assertEquals(true, currentMatchesType != updatedMatchesType)
            assertEquals(matches, fetched)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun matchesViewModel_testGetMatchesError() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val error = HttpException(
            Response.error<ResponseBody>(
                500, ResponseBody.create(MediaType.parse("plain/text"), "HTTP 500")
            )
        )

        `when`(matchesRepository.getMatches(null)).thenThrow(error)

        try {
            viewModel = MatchesViewModel(null, matchesRepository)
            viewModel.getMatchesTest(MatchesType.PREVIOUS)
        } catch (e: Exception) {
            assertEquals(error.message, e.message)
        } finally {
            Dispatchers.resetMain()
        }
    }
}