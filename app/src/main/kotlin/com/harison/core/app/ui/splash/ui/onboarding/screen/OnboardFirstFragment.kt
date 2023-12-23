package com.harison.core.app.ui.splash.ui.onboarding.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.harison.core.app.R
import com.harison.core.app.databinding.FragmentOnboardFirstBinding
import com.harison.core.app.platform.BaseFragment
import timber.log.Timber

class OnboardFirstFragment : BaseFragment<FragmentOnboardFirstBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_onboard_first

    override fun onResume() {
        Timber.tag("----screen:").d("adasdsadasdsadasdasdasdsdas")
        super.onResume()
    }
}
