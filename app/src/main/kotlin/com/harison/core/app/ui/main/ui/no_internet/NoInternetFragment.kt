package com.harison.core.app.ui.main.ui.no_internet

import android.content.Intent
import android.net.wifi.WifiManager
import androidx.navigation.fragment.findNavController
import com.ads.control.admob.AppOpenManager
import com.harison.core.app.R
import com.harison.core.app.databinding.FragmentNoInternetBinding
import com.harison.core.app.platform.BaseFragment
import com.harison.core.app.utils.NetworkUtil


class NoInternetFragment : BaseFragment<FragmentNoInternetBinding>() {
    override fun onResume() {
        super.onResume()
        if (NetworkUtil.isInternetAvailable(requireContext())) {
            findNavController().popBackStack()
        }
        AppOpenManager.getInstance().disableAppResume()
    }

    override fun setupListener() {
        binding.btnConnect.setOnClickListener {
            startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))
            AppOpenManager.getInstance().disableAdResumeByClickAction()
        }
    }

    override val layoutId: Int = R.layout.fragment_no_internet

}
