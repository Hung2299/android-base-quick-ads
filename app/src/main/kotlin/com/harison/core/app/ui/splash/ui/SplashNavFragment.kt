package com.harison.core.app.ui.splash.ui

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ads.control.admob.AppOpenManager
import com.ads.control.ads.AperoAd
import com.ads.control.ads.AperoAdCallback
import com.harison.core.app.BuildConfig
import com.harison.core.app.R
import com.harison.core.app.databinding.FragmentSplashNavBinding
import com.harison.core.app.platform.BaseFragment
import com.harison.core.app.ui.custom.ProgressBarAnimation
import com.harison.core.app.ui.splash.SplashViewModel
import com.harison.core.app.utils.BasePrefers
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashNavFragment : BaseFragment<FragmentSplashNavBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_splash_nav
    private val viewModel: SplashViewModel by viewModels()

    override fun setupUI() {
        super.setupUI()
        val anim = ProgressBarAnimation(
            binding.progressBar,
            1f,
            100f
        )
        anim.duration = 4000L
        binding.progressBar.startAnimation(anim)
        viewModel.finishCountDown = {
            if (BasePrefers.getPrefsInstance().interSplash) {
                loadAndShowInterAds()
            } else {
                navigateNextScreen()
            }
        }
    }

    private fun loadAndShowInterAds() {
        AppOpenManager.getInstance().disableAppResume()
        activity?.let {
            AperoAd.getInstance().loadSplashInterstitialAds(
                it, BuildConfig.Inter_Splash, 3000L, 5000, object : AperoAdCallback() {
                    override fun onNextAction() {
                        super.onNextAction()
                        navigateNextScreen()
                    }
                }
            )
        }
    }
    private fun navigateNextScreen() {
        val newUser = BasePrefers.getPrefsInstance().newUser
        if (newUser) {
            findNavController().navigate(R.id.firstLanguageFragment)
        } else {
            if (!BasePrefers.getPrefsInstance().openboard) {
                findNavController().navigate(R.id.onboardingFragment)
            } else {
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }
}
