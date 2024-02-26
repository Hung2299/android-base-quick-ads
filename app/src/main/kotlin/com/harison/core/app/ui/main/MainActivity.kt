package com.harison.core.app.ui.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.harison.core.app.R
import com.harison.core.app.databinding.ActivityMainBinding
import com.harison.core.app.platform.BaseActivity
import com.harison.core.app.utils.AppManager
import com.harison.core.app.utils.extensions.readAssetsFile
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var appManager: AppManager
    private var navController: NavController? = null
    private val viewModel: MainViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.activity_main

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        when (it) {
            true -> {
                Timber.tag("----").d("Permission has been granted by user")
            }

            false -> {
                Timber.tag("----").e("Permission notification has been denied")
            }
        }
    }

    private fun requestPermissionAndroid13() {
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PermissionChecker.PERMISSION_DENIED &&
                !shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val string = assets.readAssetsFile("data.json")
        Timber.d("----", string)
        viewModel.parseJSON(string)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main_fragment) as NavHostFragment
        navController = navHostFragment.navController
        requestPermissionAndroid13()
    }

    override fun onBackPressed() {
        if (navController?.currentDestination?.id != null) {
            when (navController?.currentDestination?.id) {
                R.id.splashNavFragment,
                R.id.homeFragment,
                R.id.firstLanguageFragment,
                R.id.onboardingFragment
                -> {
                    if (appManager.isBackPressFinish) {
                        finishAffinity()
                    } else {
                        Toast.makeText(this, R.string.back_pressed_finish, Toast.LENGTH_SHORT)
                            .show()
                    }
                    return
                }

                else -> {
                    navController?.popBackStack()
                    return
                }
            }
        } else onBackPressedDispatcher.onBackPressed()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
