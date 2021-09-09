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

package com.netchar.nicknamer.presentation.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.ActivityMainBinding
import com.netchar.nicknamer.presentation.infrastructure.helpers.DoublePressHandler
import com.netchar.nicknamer.presentation.infrastructure.visible

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationController: NavController

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupWithNavController()
    }

    private fun setupWithNavController() {
        val hostFragment = getNavigationFragment()
        navigationController = hostFragment.navController
        appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = setOf(R.id.main_fragment, R.id.favorites_fragment))

        setupActionBarWithNavController(navigationController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navigationController)

        navigationController.addOnDestinationChangedListener { _, destination, _ -> updateUI(destination) }
    }

    private fun getNavigationFragment(): NavHostFragment {
        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return hostFragment as NavHostFragment
    }

    private fun updateUI(destination: NavDestination) {
        if (destination.id == R.id.history_bottom_sheet_dialog_fragment) {
            return
        }

        val isTopLevelDestination = destination.isTopLevelDestination()
        binding.mainTxtTitle.visible(isTopLevelDestination)
        binding.bottomNav.visible(isTopLevelDestination)
    }

    private fun NavDestination.isTopLevelDestination(): Boolean {
        return appBarConfiguration.topLevelDestinations.contains(id)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (navigationController.navigateUp()) {
            return
        }

        doubleTapHandler.performPress()
    }

    private val doubleTapHandler = DoublePressHandler(listener = object : DoublePressHandler.Listener {
        override fun onSingleTap() {
            Toast.makeText(baseContext, getString(R.string.message_confirm_back), Toast.LENGTH_SHORT).show()
        }

        override fun onSingleTapConfirmed() {
            finishAffinity()
        }
    })
}