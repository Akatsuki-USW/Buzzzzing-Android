package com.onewx2m.buzzzzing.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.onewx2m.buzzzzing.R
import com.onewx2m.buzzzzing.databinding.ActivityMainBinding
import com.onewx2m.domain.Outcome
import com.onewx2m.domain.exception.HttpException
import com.onewx2m.domain.exception.NetworkException
import com.onewx2m.domain.exception.UnknownException
import com.onewx2m.domain.usecase.ReissueJwtUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var splashScreen: SplashScreen

    @Inject
    lateinit var reissueJwtUseCase: ReissueJwtUseCase

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var navController: NavController

    private val destinationChangedListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        binding.navBar.visibility = if(destination.id == com.onewx2m.feature_home.R.id.homeFragment) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        binding.navBar.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()

        MainScope().launch {
            val refresh =
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQK05hNFByby8yZGMrb3dJVldUUzhWY2hISFhIVnNKeEpZMDJmMFhuUy80PSIsImlhdCI6MTY4NzQxMzkyMiwidG9rZW5UeXBlIjoicmVmcmVzaCIsImV4cCI6MTY4ODYyMzUyMn0.nadwkZ7MgDqt7BbiYwiD2qwwBcRSlL2HHZkEwGHtNwqC2MePiUFxh4l5-3tqZzJLuJD-Sq4i4w-oN3kXsipIaQ"
            reissueJwtUseCase(refresh).collect {
                Timber.d("$it")
                if(it is Outcome.Failure) {
                    Timber.d("${(it.error as? NetworkException)?.message}")
                    if(it.error is HttpException) {
                        Timber.d("${(it.error as HttpException).code} ${(it.error as HttpException).body} ${(it.error as HttpException).message}")
                    }
                }
            }
        }

        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onPause() {
        super.onPause()

        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }
}