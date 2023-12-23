package com.harison.core.app.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ads.control.ads.AperoAd
import com.ads.control.ads.wrapper.ApNativeAd
import com.facebook.shimmer.ShimmerFrameLayout
import timber.log.Timber

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag("onCreate:").d(this.javaClass.simpleName)
        setupData()
        setupUI()
        setupListener()
    }

    protected abstract val layoutId: Int
    protected lateinit var binding: T
    override fun onResume() {
        Timber.tag("----screen:").d(this.javaClass.simpleName)
        super.onResume()
    }

    protected open fun setupUI() {}
    protected open fun setupData() {}
    protected open fun setupListener() {}
    fun requireContext(action: (nonNullContext: Context) -> Unit) {
        context?.let(action)
    }

    fun populateNative(
        adsNative: ApNativeAd,
        adPlaceHolder: FrameLayout,
        shimmer: ShimmerFrameLayout
    ) {
        activity?.let {
            AperoAd.getInstance().populateNativeAdView(
                it, adsNative, adPlaceHolder, shimmer
            )
        }
    }

    fun toast(message: String, isLong: Boolean? = false) {
        context?.let {
            Toast.makeText(
                it,
                message,
                if (isLong == false) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
            )
                .show()
        }
    }
}
