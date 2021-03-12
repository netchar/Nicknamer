package com.netchar.nicknamer.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.netchar.nicknamer.App
import com.netchar.nicknamer.R
import com.netchar.nicknamer.domen.Config


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var nickname: TextView

    private val generator by lazy {
        val applicationContext = requireContext().applicationContext
        val app = applicationContext as App
        app.nicknameGenerator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nickname = view.findViewById(R.id.main_tv_nickname)

        view.findViewById<Button>(R.id.main_btn_copy_to_clipboard).setOnClickListener {
            requireContext().copyToClipboard(nickname.text)
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show()
        }

        view.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                val generatedNickname = generator.generate(Config((2..10).random(), Config.Gender.INDIFFERENT))
                nickname.text = generatedNickname
            }
        }
    }
}