package com.harison.core.app.ui.splash.ui.first_language

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ads.control.admob.AppOpenManager
import com.ads.control.ads.AperoAd
import com.ads.control.ads.AperoAdCallback
import com.ads.control.ads.wrapper.ApAdError
import com.ads.control.ads.wrapper.ApNativeAd
import com.harison.core.app.BuildConfig
import com.harison.core.app.R
import com.harison.core.app.databinding.FragmentFirstLanguageBinding
import com.harison.core.app.platform.BaseFragment
import com.harison.core.app.ui.main.ui.setting.language.LanguageItem
import com.harison.core.app.ui.main.ui.setting.language.LanguageViewModel
import com.harison.core.app.utils.*
import com.harison.core.app.utils.extensions.setGradientBackground
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import java.util.Locale

data class Language(
    val id: Int, val name: String, val code: String, val image: Int
)

class FirstLanguageFragment : BaseFragment<FragmentFirstLanguageBinding>(),
    LanguageItem.OnLanguageListener {

    private lateinit var groupLanguage: GroupAdapter<GroupieViewHolder>
    private lateinit var locales: ArrayList<Language>

    private val languageViewModel: LanguageViewModel by viewModels()
    private var newLocale: String? = null
    private var isViewCreated = false

    override fun setupData() {
        super.setupData()
        groupLanguage = GroupAdapter()
        groupLanguage.clear()
        initLanguage()
    }

    override fun setupUI() {
        super.setupUI()
        binding.languageSettingRcv.apply {
            setHasFixedSize(false)
            requireContext { layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false) }
            adapter = groupLanguage
        }
        if (BasePrefers.getPrefsInstance().nativeLanguage && isShowNativeLanguage()) {
            loadNativeFirstLanguage()
        } else {
            binding.frNativeAdsActivity.visibility = View.GONE
        }
        binding.settingLanguageToolbar.setGradientBackground(
            R.color.start_blue_gradient,
            R.color.end_blue_gradient
        )
    }

    override fun setupListener() {
        super.setupListener()
        binding.choseBtn.setOnClickListener {
            newLocale = languageViewModel.chosenLanguage?.code ?: Locale.getDefault().displayLanguage
            if (newLocale != null) {
                BasePrefers.getPrefsInstance().locale =
                    newLocale ?: Locale.getDefault().displayLanguage
                requireContext {
                    val wrapContext = MyContextWrapper.wrap(
                        it, newLocale!!
                    )
                    resources.updateConfiguration(
                        wrapContext.resources.configuration, wrapContext.resources.displayMetrics
                    )
                }
            }
            goToOnboarding()
        }
    }

    override fun onResume() {
        super.onResume()
        AppOpenManager.getInstance().enableAppResume()
    }

    private fun goToOnboarding() {
        BasePrefers.getPrefsInstance().newUser = false
        findNavController().navigate(R.id.onboardingFragment)
    }

    private fun initLanguage() {
        locales = ContextUtils.getLocalesListFirt(resources)
        locales.forEach {
            groupLanguage.add(LanguageItem(it, this, languageViewModel))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onChooseLanguage(language: Language, position: Int) {
        languageViewModel.chosenLanguage = language
        groupLanguage.notifyDataSetChanged()
    }

    private fun loadNativeFirstLanguage() {
        AperoAd.getInstance().loadNativeAdResultCallback(activity,
            BuildConfig.Native_Language,
            R.layout.native_language_first_open,
            object : AperoAdCallback() {
                override fun onNativeAdLoaded(nativeAd: ApNativeAd) {
                    addNativeAds(nativeAd)
                }

                override fun onAdFailedToLoad(adError: ApAdError?) {
                    removeNativeAds()
                }
            })
    }

    private fun removeNativeAds() {
        binding.apply {
            shimmerContainerNative1.stopShimmer()
            shimmerContainerNative1.visibility = View.GONE
            flAdplaceholderActivity.visibility = View.GONE
        }
    }

    private fun addNativeAds(native: ApNativeAd) {
        binding.apply {
            shimmerContainerNative1.stopShimmer()
            shimmerContainerNative1.visibility = View.GONE
            flAdplaceholderActivity.visibility = View.VISIBLE
            populateNative(native, flAdplaceholderActivity, shimmerContainerNative1)
        }
    }


    override fun onDestroy() {
        isViewCreated = false
        super.onDestroy()
    }

    override val layoutId: Int
        get() = R.layout.fragment_first_language
}
