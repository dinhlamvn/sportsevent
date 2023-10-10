package com.inspius.sportsevent.ui.teammatches

import android.os.Bundle
import androidx.core.os.bundleOf
import com.inspius.sportsevent.R
import com.inspius.sportsevent.base.BaseActivity
import com.inspius.sportsevent.common.AppExtras
import com.inspius.sportsevent.databinding.ActivityTeamMatchesBinding
import com.inspius.sportsevent.extensions.toast
import com.inspius.sportsevent.ui.matches.MatchesFragment

class TeamMatchesActivity : BaseActivity<ActivityTeamMatchesBinding>() {

    override fun createViewBinding(): ActivityTeamMatchesBinding {
        return ActivityTeamMatchesBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val teamId = intent.getStringExtra(AppExtras.EXTRA_TEAM_ID) ?: run {
            toast(getString(R.string.team_id_not_found))
            finish()
        }

        binding.toolbar.title = intent.getStringExtra(AppExtras.EXTRA_TEAM_NAME)
            ?: getString(R.string.title_activity_team_matches)

        val args = bundleOf(AppExtras.EXTRA_TEAM_ID to teamId)

        supportFragmentManager.beginTransaction().replace(R.id.container, MatchesFragment().apply {
            arguments = args
        }).commit()
    }
}