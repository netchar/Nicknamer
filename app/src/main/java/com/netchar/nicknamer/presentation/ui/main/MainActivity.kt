package com.netchar.nicknamer.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.netchar.nicknamer.R
import com.netchar.nicknamer.databinding.ActivityMainBinding
import com.netchar.nicknamer.presentation.visible

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val navigationController: NavController by lazy {
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val hostFragment = fragment as NavHostFragment
        hostFragment.navController
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupWithNavController()
    }

    private fun setupWithNavController() {
        navigationController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.mainTxtTitle.visible(destination.id == R.id.main_fragment)
        }
        appBarConfiguration = AppBarConfiguration(navigationController.graph)
        setupActionBarWithNavController(navigationController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}