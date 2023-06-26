package com.onewx2m.buzzzzing.ui

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.onewx2m.buzzzzing.R
import com.onewx2m.buzzzzing.databinding.ActivityMainBinding
import com.onewx2m.mvi.MviActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    MviActivity<ActivityMainBinding, MainViewState, MainEvent, MainSideEffect, MainViewModel>(
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
        initSplashScreen()
        viewModel.reissueJwtAndNavigateFragmentAndHideSplashScreen()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        binding.navBar.setupWithNavController(navController)
    }

    private fun initSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.preDrawRemoveFlag) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    override fun render(state: MainViewState) {
        super.render(state)

        binding.navBar.visibility = if(state.isBottomNavigationBarVisible) View.VISIBLE else View.GONE
        viewModel.preDrawRemoveFlag = state.isSplashScreenVisible.not()
    }

    override fun handleSideEffect(sideEffect: MainSideEffect) {
        super.handleSideEffect(sideEffect)

        when(sideEffect) {
            MainSideEffect.GoToHomeFragment -> {
                goToHomeFragment()
            }
        }
    }

    private fun goToHomeFragment() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(getString(com.onewx2m.core_ui.R.string.deeplink_home_fragment).toUri())
            .build()

        val navOptions = NavOptions.Builder()
            .setPopUpTo(com.onewx2m.feature_login_signup.R.id.loginFragment, true)
            .build()

        navController.navigate(request, navOptions)
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