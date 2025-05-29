package com.example.kolsa_test_app

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kolsa.app.MainContract
import ru.kolsa.app.MainViewModel
import ru.kolsa.core.ui.extensions.collectFlowWithLifecycle

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private var lastState: MainContract.State? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val navHostFragment =
            findViewById<FragmentContainerView>(
                R.id.nav_host_fragment
            ).getFragment<NavHostFragment>()
        val navController = navHostFragment.navController
        collectFlowWithLifecycle(viewModel.state) {
            observeState(it, navController)
        }
    }

    private fun observeState(state: MainContract.State, navController: NavController) {
        when (state) {
            MainContract.State.Main -> {
                if (lastState !is MainContract.State.Main) {
                    navController.popBackStack(R.id.splashFragment, false)
                    navController.navigate(R.id.action_splashFragment_to_workouts_nav_graph)
                    navController.clearBackStack(R.id.splashFragment)
                }
            }

            MainContract.State.None -> {}

            MainContract.State.Splash -> {
                navController.popBackStack(R.id.splashFragment, false)
            }
        }
        lastState = state
    }
}