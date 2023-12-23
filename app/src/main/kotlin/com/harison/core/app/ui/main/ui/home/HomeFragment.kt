package com.harison.core.app.ui.main.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.harison.core.app.R
import com.harison.core.app.databinding.FragmentHomeBinding
import com.harison.core.app.platform.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_home

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun setupListener() {
        binding.testButton.setOnClickListener {
            Timber.d("---insertSample")
            viewModel.insertSample()
        }
    }
}
