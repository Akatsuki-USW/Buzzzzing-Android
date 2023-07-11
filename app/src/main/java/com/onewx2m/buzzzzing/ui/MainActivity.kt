package com.onewx2m.buzzzzing.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.onewx2m.buzzzzing.R
import com.onewx2m.buzzzzing.databinding.ActivityMainBinding
import com.onewx2m.core_ui.util.DeepLinkUtil
import com.onewx2m.design_system.components.toast.ErrorToast
import com.onewx2m.mvi.MviActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    MviActivity<ActivityMainBinding, MainViewState, MainEvent, MainSideEffect, MainViewModel>(
        ActivityMainBinding::inflate,
    ) {
    private lateinit var splashScreen: SplashScreen

    override val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController

    private val destinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            viewModel.isDestinationInBottomNavigationBarInitialFragment(
                destination.id,
            )
        }

    private val inputMethodManager: InputMethodManager
        get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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

        ViewCompat.setOnApplyWindowInsetsListener(window.decorView.rootView) { _, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            viewModel.doWhenKeyboardVisibilityChange(
                imeVisible,
                navController.currentDestination?.id,
            )
            insets
        }
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
            },
        )
    }

    override fun render(state: MainViewState) {
        super.render(state)

        binding.navBar.visibility =
            if (state.isBottomNavigationBarVisible) View.VISIBLE else View.GONE
        viewModel.preDrawRemoveFlag = state.isSplashScreenVisible.not()
    }

    override fun handleSideEffect(sideEffect: MainSideEffect) {
        super.handleSideEffect(sideEffect)

        when (sideEffect) {
            MainSideEffect.GoToHomeFragment -> {
                goToHomeFragment()
            }

            MainSideEffect.FinishActivity -> finish()
            is MainSideEffect.ShowErrorToast -> ErrorToast.make(binding.root, sideEffect.msg).show()
        }
    }

    private fun goToHomeFragment() {
        val (request, navOptions) = DeepLinkUtil.getHomeRequestAndOption(
            this,
            com.onewx2m.feature_login_signup.R.id.loginFragment,
            true,
        )
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

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val currentFocusView = currentFocus
            if (currentFocusView is EditText) {
                val outRect = getGlobalVisibleRect(currentFocusView)
                if (isTouchEventCoordinatesInOutRect(event, outRect).not()) {
                    currentFocusView.clearFocus()
                    inputMethodManager.hideSoftInputFromWindow(currentFocusView.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun getGlobalVisibleRect(view: View): Rect {
        val viewOutRect = Rect()
        view.getGlobalVisibleRect(viewOutRect)

        val rootOutRect = Rect()
        binding.root.getGlobalVisibleRect(rootOutRect)
        viewOutRect.bottom = rootOutRect.bottom
        return viewOutRect
    }

    private fun isTouchEventCoordinatesInOutRect(event: MotionEvent, outRect: Rect): Boolean {
        return outRect.contains(event.rawX.toInt(), event.rawY.toInt())
    }
}
