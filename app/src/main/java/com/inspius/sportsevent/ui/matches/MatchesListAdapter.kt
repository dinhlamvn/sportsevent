package com.inspius.sportsevent.ui.matches

import android.graphics.Color
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.inSpans
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.inspius.sportsevent.base.BaseListAdapter
import com.inspius.sportsevent.databinding.MatchesListItemBinding
import com.inspius.sportsevent.extensions.parseDatetimeUTC
import com.inspius.sportsevent.model.Matches
import com.inspius.sportsevent.model.MatchesType

class MatchesListAdapter(private val listener: MatchesListActionCallback) :
    BaseListAdapter<Matches.Data>() {

    interface MatchesListActionCallback {
        fun onViewHighlights(highlights: String)
        fun onReminder(data: Matches.Data)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BaseViewHolder<ViewBinding, Matches.Data> {
        return MatchesViewHolder(
            MatchesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            listener
        ) as BaseViewHolder<ViewBinding, Matches.Data>
    }

    private inner class MatchesViewHolder(
        private val binding: MatchesListItemBinding, private val listener: MatchesListActionCallback
    ) : BaseViewHolder<MatchesListItemBinding, Matches.Data>(binding) {

        init {
            binding.textHighlights.movementMethod = LinkMovementMethod.getInstance()
        }

        override fun bind(model: Matches.Data) {
            binding.imageNotify.setOnClickListener {
                listener.onReminder(model)
            }
            binding.imageNotify.isVisible = model.type == MatchesType.UPCOMING

            binding.textHome.text = buildSpannedString {
                append("Home: ")
                color(Color.RED) {
                    append(model.home)
                }
            }
            binding.textAway.text = buildSpannedString {
                append("Away: ")
                color(Color.BLUE) {
                    append(model.away)
                }
            }
            binding.textDesc.text = model.description
            binding.textDate.text = model.date.parseDatetimeUTC()

            binding.textWinner.apply {
                model.winner?.let { winner ->
                    text = buildSpannedString {
                        append("Winner: ")
                        color(Color.GREEN) {
                            append(winner)
                        }
                    }
                    isVisible = true
                } ?: run {
                    text = null
                    isVisible = false
                }
            }

            binding.textHighlights.apply {
                model.highlights?.let { highlights ->
                    text = buildSpannedString {
                        append("Highlights: ")
                        inSpans(object : ClickableSpan() {
                            override fun onClick(view: View) {
                                listener.onViewHighlights(highlights)
                            }
                        }) {
                            append(highlights)
                        }
                    }
                    isVisible = true
                } ?: run {
                    text = null
                    isVisible = false
                }
            }
        }
    }
}