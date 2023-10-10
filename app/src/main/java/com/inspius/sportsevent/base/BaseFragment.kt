package com.inspius.sportsevent.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    abstract fun createViewBinding(layoutInflater: LayoutInflater, container: ViewGroup?): VB

    protected lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = createViewBinding(inflater, container)
        return binding.root
    }
}