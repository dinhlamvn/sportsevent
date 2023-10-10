package com.inspius.sportsevent.ui.teams

import androidx.lifecycle.viewModelScope
import com.inspius.sportsevent.base.BaseViewModel
import com.inspius.sportsevent.extensions.parseHttpException
import com.inspius.sportsevent.model.Team
import com.inspius.sportsevent.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TeamsViewModel(private val teamRepository: TeamRepository) : BaseViewModel() {

    private val _uiState = MutableStateFlow<TeamsUiState>(TeamsUiState.Loading)
    val uiState: StateFlow<TeamsUiState> = _uiState

    init {
        getTeams()
    }

    fun getTeams() {
        viewModelScope.launch {
            teamRepository.getTeams()
                .onStart {
                    _uiState.value = TeamsUiState.Loading
                }
                .catch { error ->
                    _uiState.value = TeamsUiState.Error(error.parseHttpException())
                }
                .collect { teams ->
                    _uiState.value = TeamsUiState.Success(teams)
                }
        }
    }
}

sealed class TeamsUiState(val showLoading: Boolean) {
    data object Loading : TeamsUiState(true)
    data class Success(val teams: List<Team>) : TeamsUiState(false)
    data class Error(val exception: Throwable) : TeamsUiState(false)
}