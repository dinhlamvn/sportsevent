package com.inspius.sportsevent.ui.matches

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.inspius.sportsevent.base.BaseViewModel
import com.inspius.sportsevent.extensions.parseHttpException
import com.inspius.sportsevent.model.Matches
import com.inspius.sportsevent.model.MatchesType
import com.inspius.sportsevent.repository.MatchesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MatchesViewModel(
    private val teamId: String?, private val matchesRepository: MatchesRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<MatchesUiState>(MatchesUiState.Loading)
    val uiState: StateFlow<MatchesUiState> = _uiState

    init {
        getMatches(MatchesType.PREVIOUS)
    }

    private fun getMatches(matchesType: MatchesType) {
        viewModelScope.launch {
            matchesRepository.getMatches(teamId).onStart {
                _uiState.value = MatchesUiState.Loading
            }.catch { error ->
                _uiState.value = MatchesUiState.Error(matchesType, error.parseHttpException())
            }.collect { data ->
                _uiState.value = MatchesUiState.Success(
                    matchesType,
                    data
                )
            }
        }
    }

    @VisibleForTesting
    fun getMatchesTest(matchesType: MatchesType) {
        getMatches(matchesType)
    }

    fun updateMatchesType(matchesType: MatchesType) {
        val value = uiState.value
        if (value is MatchesUiState.Success) {
            _uiState.value = value.copy(matchesType = matchesType)
        } else {
            getMatches(matchesType)
        }
    }

    fun doOnRefresh() {
        getMatches(uiState.value.matchesType)
    }
}

sealed class MatchesUiState(open val matchesType: MatchesType, val showLoading: Boolean) {
    data object Loading : MatchesUiState(MatchesType.PREVIOUS, true)
    data class Success(
        override val matchesType: MatchesType, val data: Matches
    ) : MatchesUiState(matchesType, false)

    data class Error(override val matchesType: MatchesType, val exception: Throwable) :
        MatchesUiState(matchesType, false)
}