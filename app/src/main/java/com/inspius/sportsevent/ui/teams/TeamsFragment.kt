package com.inspius.sportsevent.ui.teams

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.inspius.sportsevent.base.BaseFragment
import com.inspius.sportsevent.common.AppExtras
import com.inspius.sportsevent.databinding.FragmentTeamsBinding
import com.inspius.sportsevent.extensions.toast
import com.inspius.sportsevent.model.Team
import com.inspius.sportsevent.repository.TeamRepository
import com.inspius.sportsevent.ui.teammatches.TeamMatchesActivity
import kotlinx.coroutines.launch

class TeamsFragment : BaseFragment<FragmentTeamsBinding>(), TeamListAdapter.TeamListActionCallback {

    private val viewModel: TeamsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TeamsViewModel::class.java)) {
                    return TeamsViewModel(TeamRepository()) as T
                }
                throw IllegalArgumentException("Invalid ViewModel class ${modelClass.name}")
            }
        }
    }

    private val teamAdapter = TeamListAdapter(this)

    override fun createViewBinding(
        layoutInflater: LayoutInflater, container: ViewGroup?
    ): FragmentTeamsBinding {
        return FragmentTeamsBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.getTeams()
        }

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(), DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.adapter = teamAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleUiState)
            }
        }

        viewModel.toastLiveData.observe(viewLifecycleOwner, this::toast)
    }

    private fun handleUiState(uiState: TeamsUiState) {
        binding.frameLoading.isVisible = uiState.showLoading
        when (uiState) {
            is TeamsUiState.Success -> onTeamsLoaded(uiState.teams)
            is TeamsUiState.Error -> showError(uiState.exception)
            else -> {}
        }
    }

    private fun showError(error: Throwable) {
        toast(error.message!!)
    }

    private fun onTeamsLoaded(teams: List<Team>) {
        teamAdapter.submitList(teams)
        binding.textMessage.isVisible = teams.isEmpty()
    }

    override fun onItemClicked(teamId: String, teamName: String) {
        Intent(requireContext(), TeamMatchesActivity::class.java)
            .putExtra(AppExtras.EXTRA_TEAM_ID, teamId)
            .putExtra(AppExtras.EXTRA_TEAM_NAME, teamName)
            .also(this::startActivity)
    }
}