package com.onewx2m.buzzzzing.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.onewx2m.buzzzzing.R
import com.onewx2m.buzzzzing.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    private val destinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        binding.navBar.visibility = if(destination.id == com.onewx2m.feature_home.R.id.homeFragment) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        binding.navBar.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()

        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onPause() {
        super.onPause()

        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }
}