package com.inspius.sportsevent.viewmodel

import com.inspius.sportsevent.data.getTeams
import com.inspius.sportsevent.repository.TeamRepository
import com.inspius.sportsevent.ui.teams.TeamsUiState
import com.inspius.sportsevent.ui.teams.TeamsViewModel
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
class TeamsViewModelTest {

    @Mock
    lateinit var teamsRepository: TeamRepository

    private lateinit var viewModel: TeamsViewModel

    @Test
    @ExperimentalCoroutinesApi
    fun teamsViewModel_testGetTeamsSuccess() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        val teams = getTeams()
        `when`(teamsRepository.getTeams()).thenReturn(flowOf(teams))

        try {
            viewModel = TeamsViewModel(teamsRepository)
            viewModel.getTeams()
            val fetchedTeams = (viewModel.uiState.value as? TeamsUiState.Success)?.teams
            assertEquals(teams, fetchedTeams)
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun teamsViewModel_testGetTeamsError() = runTest {
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)

        val error = HttpException(
            Response.error<ResponseBody>(
                500, ResponseBody.create(MediaType.parse("plain/text"), "HTTP 500")
            )
        )

        `when`(teamsRepository.getTeams()).thenThrow(error)

        try {
            viewModel = TeamsViewModel(teamsRepository)
            viewModel.getTeams()
        } catch (e: Exception) {
            assertEquals(error.message, e.message)
        } finally {
            Dispatchers.resetMain()
        }
    }
}