package com.onewx2m.buzzzzing.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
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
import com.onewx2m.mvi.Event
import com.onewx2m.mvi.MviActivity
import com.onewx2m.mvi.SideEffect
import com.onewx2m.mvi.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity :
    MviActivity<ActivityMainBinding, MainViewState, MainEvent, SideEffect.Default, MainViewModel>(
        ActivityMainBinding::inflate
    ) {
    private lateinit var splashScreen: SplashScreen

    override val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController

    private val bottomNavigationBarInitialFragmentIds = listOf(com.onewx2m.feature_home.R.id.homeFragment)

    private val destinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            viewModel.isDestinationInBottomNavigationBarInitialFragment(destination.id, bottomNavigationBarInitialFragmentIds)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun initView() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        binding.navBar.setupWithNavController(navController)
    }

    override fun render(state: MainViewState) {
        super.render(state)

        binding.navBar.visibility = if(state.isBottomNavigationBarVisible) View.VISIBLE else View.GONE
    }

    override fun onResume() {
        super.onResume()

        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onStop() {
        super.onStop()

        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }
}