package com.netchar.nicknamer.presentation.ui.history

import android.view.ViewGroup
import androidx.core.util.Consumer
import com.netchar.nicknamer.databinding.RowHistoryBinding
import com.netchar.nicknamer.presentation.infrastructure.helpers.BindableViewHolder
import com.netchar.nicknamer.presentation.infrastructure.helpers.DefaultDiffCallback
import com.netchar.nicknamer.presentation.infrastructure.inflater
import com.netchar.nicknamer.presentation.ui.favorites.BindableListAdapter
import com.netchar.nicknamer.presentation.ui.main.MainViewModel

class HistoryAdapter(private val listener: Consumer<String>) : BindableListAdapter<MainViewModel.NicknameItem, HistoryAdapter.HistoryViewHolder>(DefaultDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(RowHistoryBinding.inflate(parent.inflater(), parent, false).apply {
            handler = listener
        })
    }

    class HistoryViewHolder(private val binding: RowHistoryBinding) : BindableViewHolder<MainViewModel.NicknameItem>(binding.root) {
        override fun bind(model: MainViewModel.NicknameItem) {
            binding.model = model
            binding.executePendingBindings()
        }
    }
}