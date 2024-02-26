package com.harison.core.app.ui.splash.ui.onboarding.screen

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.harison.core.app.R
import com.harison.core.app.databinding.FragmentOnboardFirstBinding
import com.harison.core.app.platform.BaseFragment
import com.harison.core.app.ui.splash.ui.onboarding.OnboardingViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class OnboardFirstFragment : BaseFragment<FragmentOnboardFirstBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_onboard_first

    val sharedViewModel: OnboardingViewModel by viewModels(
        ownerProducer = { parentFragment ?: this }
    )

    override fun onResume() {
        Timber.tag("----screen:").d("adasdsadasdsadasdasdasdsdas")
        lifecycleScope.launch {
            repeat(100) {
                delay(1000)
                Timber.e("----num share OB1 ${sharedViewModel.num}")
            }
        }
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.e("----num share OB1 onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.e("----num share OB1 onDestroyView")
    }
}
