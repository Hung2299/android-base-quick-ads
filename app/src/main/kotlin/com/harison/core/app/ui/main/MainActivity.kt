package com.harison.core.app.ui.main

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.harison.core.app.R
import com.harison.core.app.databinding.ActivityMainBinding
import com.harison.core.app.platform.BaseActivity
import com.harison.core.app.utils.AppManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var appManager: AppManager
    private var connectivityManager: ConnectivityManager? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var navController: NavController? = null
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
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main_fragment) as NavHostFragment
        navController = navHostFragment.navController
        listenForNetworkState()
        checkNetworkState()
        requestPermissionAndroid13()
    }

    private fun checkNetworkState() {
        val networkInfo = connectivityManager?.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnected) {
            runOnUiThread {
                navController?.navigate(R.id.noInternetFragment)
            }
        }
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

    private fun listenForNetworkState() {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    if (navController?.currentDestination?.id == R.id.noInternetFragment) {
                        navController?.popBackStack()
                    }
                }
            }

            override fun onLost(network: Network) {
                runOnUiThread {
                    navController?.navigate(R.id.noInternetFragment)
                }
            }
        }
        connectivityManager?.registerDefaultNetworkCallback(networkCallback as ConnectivityManager.NetworkCallback)
    }
}
