package com.netchar.nicknamer.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentMainBinding
import com.netchar.nicknamer.presentation.copyToClipboard
import com.netchar.nicknamer.presentation.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<MainViewModel> { MainViewModelFactory() }
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        observe()
    }

    private fun bindViews() = with(binding) {
        mainTvNickname.setOnClickListener {
            copyToClipboard()
        }

        mainBtnGenerate.setOnClickListener {
            viewModel.generateNewNickname()
        }

        mainRadioGrpGender.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setGender(checkedId)
        }

        mainRadioGrpAlphabet.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setAlphabet(checkedId)
        }

        mainSlideLength.addOnChangeListener { _, value, _ ->
            viewModel.setLength(value)
        }
    }

    private fun observe() {
        viewModel.nickname.observe(viewLifecycleOwner, { nickname ->
            binding.mainTvNickname.text = nickname.value
        })
    }

    private fun copyToClipboard() {
        val context = requireContext()
        context.copyToClipboard(binding.mainTvNickname.text)
        Snackbar.make(requireView(), "Copied to clipboard", Snackbar.LENGTH_SHORT).show()
    }
}

