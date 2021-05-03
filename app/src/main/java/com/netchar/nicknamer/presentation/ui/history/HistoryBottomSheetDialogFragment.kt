/*
 * Copyright (c) 2021 Eugene Glushankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netchar.nicknamer.presentation.ui.history

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentHistoryBinding
import com.netchar.nicknamer.presentation.infrastructure.bind
import com.netchar.nicknamer.presentation.infrastructure.viewDataBinding
import com.netchar.nicknamer.presentation.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HistoryBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private val binding: FragmentHistoryBinding by viewDataBinding(R.layout.fragment_history)
    private val viewModel by sharedViewModel<MainViewModel>()

    private val adapter = HistoryAdapter { nickname ->
        viewModel.copyToClipboard(nickname)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyRecycler.adapter = adapter
        binding.bind(this, viewModel)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.invalidateCurrentNicknameState()
    }
}