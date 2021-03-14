package com.netchar.nicknamer.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.FragmentMainBinding
import com.netchar.nicknamer.domen.models.Config
import com.netchar.nicknamer.presentation.copyToClipboard
import com.netchar.nicknamer.presentation.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by viewModels<MainViewModel>() { MainViewModelFactory() }
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind()
        observe()
    }

    private fun bind() {
        binding.mainTvNickname.setOnLongClickListener {
            copyToClipboard()
            true
        }

        binding.mainBtnGenerate.setOnClickListener {
            viewModel.generateNewNickname(Config(nicknameLength, gender, alphabet))
        }

        binding.mainBtnGenerate.performClick()
    }

    private fun observe() {
        viewModel.nickname.observe(viewLifecycleOwner, Observer { nickname ->
            binding.mainTvNickname.text = nickname.value
        })
    }

    private val nicknameLength: Int get() = binding.mainSlideLength.value.toInt()

    private val gender: Config.Gender
        get() = when (binding.mainRadioGrpGender.checkedRadioButtonId) {
            R.id.main_radio_btn_male -> Config.Gender.MALE
            else -> Config.Gender.FEMALE
        }

    private val alphabet: Config.Alphabet
        get() = when (binding.mainRadioGrpAlphabet.checkedRadioButtonId) {
            R.id.main_radio_btn_latin -> Config.Alphabet.LATIN
            else -> Config.Alphabet.CYRILLIC
        }

    private fun copyToClipboard() {
        requireContext().copyToClipboard(binding.mainTvNickname.text)
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show()
    }
}

