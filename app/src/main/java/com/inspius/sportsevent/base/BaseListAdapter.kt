package com.inspius.sportsevent.base

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

abstract class BaseListAdapter<T : BaseListAdapter.BaseListModel> :
    ListAdapter<T, BaseListAdapter.BaseViewHolder<ViewBinding, T>>(ListDiffCallback()) {

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding, T>, position: Int) {
        holder.bind(getItem(position))
    }

    abstract class BaseListModel {
        abstract fun isSame(other: BaseListModel): Boolean
        abstract fun isContentSame(other: BaseListModel): Boolean
    }

    abstract class BaseViewHolder<VB : ViewBinding, T : BaseListModel>(private val binding: VB) :
        ViewHolder(binding.root) {

        protected val context: Context = itemView.context

        abstract fun bind(model: T)
    }

    private class ListDiffCallback<T : BaseListModel> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.isSame(newItem)
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.isContentSame(newItem)
        }
    }
}