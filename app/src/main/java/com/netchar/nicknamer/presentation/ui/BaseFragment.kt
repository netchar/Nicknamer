package com.netchar.nicknamer.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.netchar.nicknamer.presentation.infrastructure.bind
import com.netchar.nicknamer.presentation.infrastructure.viewDataBinding

abstract class BaseFragment<TViewModel : ViewModel, TViewBinding : ViewDataBinding>(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {
    abstract val viewModel: TViewModel
    protected val binding: TViewBinding by viewDataBinding(layoutRes)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bind(this, viewModel)
    }
}
