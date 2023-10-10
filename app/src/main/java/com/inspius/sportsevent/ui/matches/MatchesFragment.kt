package com.inspius.sportsevent.ui.matches

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import com.inspius.sportsevent.R
import com.inspius.sportsevent.base.BaseFragment
import com.inspius.sportsevent.common.AppExtras
import com.inspius.sportsevent.databinding.FragmentMatchesBinding
import com.inspius.sportsevent.extensions.toast
import com.inspius.sportsevent.model.Matches
import com.inspius.sportsevent.model.MatchesType
import com.inspius.sportsevent.repository.MatchesRepository
import com.inspius.sportsevent.util.AlarmUtil
import kotlinx.coroutines.launch

class MatchesFragment : BaseFragment<FragmentMatchesBinding>(), Toolbar.OnMenuItemClickListener,
    MatchesListAdapter.MatchesListActionCallback {

    private val viewModel: MatchesViewModel by viewModels {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MatchesViewModel::class.java)) {
                    return MatchesViewModel(
                        arguments?.getString(AppExtras.EXTRA_TEAM_ID), MatchesRepository()
                    ) as T
                }
                throw IllegalArgumentException("Invalid ViewModel class ${modelClass.name}")
            }
        }
    }

    private val matchesAdapter = MatchesListAdapter(this)

    override fun createViewBinding(
        layoutInflater: LayoutInflater, container: ViewGroup?
    ): FragmentMatchesBinding {
        return FragmentMatchesBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.inflateMenu(R.menu.matches_menu)
        binding.toolbar.setOnMenuItemClickListener(this)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = false
            viewModel.doOnRefresh()
        }

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(), DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.adapter = matchesAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleUiState)
            }
        }

        viewModel.toastLiveData.observe(viewLifecycleOwner, this::toast)
    }

    private fun handleUiState(uiState: MatchesUiState) {
        binding.frameLoading.isVisible = uiState.showLoading
        when (uiState) {
            is MatchesUiState.Success -> onMatchesLoaded(uiState.matchesType, uiState.data)
            is MatchesUiState.Error -> showError(uiState.exception)
            else -> {}
        }
    }

    private fun showError(error: Throwable) {
        toast(error.message!!)
    }

    private fun onMatchesLoaded(matchesType: MatchesType, data: Matches) {
        binding.toolbar.title = matchesType.type
        val list = getList(matchesType, data)
        matchesAdapter.submitList(list)
        binding.textMessage.isVisible = list.isEmpty()
    }

    private fun getList(matchesType: MatchesType, data: Matches): List<Matches.Data> {
        return if (matchesType == MatchesType.PREVIOUS) data.previous else data.upcoming
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val matchesType = when (item?.itemId) {
            R.id.item_previous -> MatchesType.PREVIOUS
            else -> MatchesType.UPCOMING
        }
        viewModel.updateMatchesType(matchesType)
        return true
    }

    override fun onViewHighlights(highlights: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(highlights))
            .addCategory(Intent.CATEGORY_DEFAULT)
        try {
            startActivity(intent)
        } catch (e: Exception) {
            toast(getString(R.string.no_app_resolve_action))
        }
    }

    override fun onReminder(data: Matches.Data) {
        AlarmUtil.scheduleUpcomingMatchesReminder(requireContext(), data)
    }
}