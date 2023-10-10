package com.inspius.sportsevent.ui.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.inspius.sportsevent.base.BaseListAdapter
import com.inspius.sportsevent.databinding.TeamListItemBinding
import com.inspius.sportsevent.imageloader.ImageLoader
import com.inspius.sportsevent.model.Team

class TeamListAdapter(private val listener: TeamListActionCallback) : BaseListAdapter<Team>() {

    interface TeamListActionCallback {
        fun onItemClicked(teamId: String, teamName: String)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding, Team> {
        return TeamViewHolder(
            TeamListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            ),
            listener
        ) as BaseViewHolder<ViewBinding, Team>
    }

    private inner class TeamViewHolder(
        private val binding: TeamListItemBinding,
        private val listener: TeamListActionCallback
    ) :
        BaseViewHolder<TeamListItemBinding, Team>(binding) {

        override fun bind(model: Team) {
            binding.textName.text = model.name
            ImageLoader.loader.load(model.logo, binding.imageLogo)

            binding.root.setOnClickListener {
                listener.onItemClicked(model.id, model.name)
            }
        }
    }
}