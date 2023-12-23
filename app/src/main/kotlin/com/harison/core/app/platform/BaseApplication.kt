package com.harison.core.app.platform

import android.app.Application
import com.ads.control.admob.Admob
import com.ads.control.admob.AppOpenManager
import com.ads.control.ads.AperoAd
import com.ads.control.config.AperoAdConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.harison.core.app.BuildConfig
import com.harison.core.app.R
import com.harison.core.app.ui.main.MainActivity
import com.harison.core.app.utils.BasePrefers
import com.harison.core.app.utils.Constants
import com.harison.core.app.utils.timber.CrashlyticsTree
import com.harison.core.app.utils.timber.MyDebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {
    private var aperoAdConfig: AperoAdConfig? = null

    override fun onCreate() {
        super.onCreate()

        BasePrefers.initPrefs(applicationContext)
        initRemoteConfig()
        initAds()
        fetchRemoteConfig()
        setupTimber()
    }

    private fun fetchRemoteConfig() {
        val config = FirebaseRemoteConfig.getInstance()
        val configSettings =
            FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(0).build()
        config.setConfigSettingsAsync(configSettings)
        config.setDefaultsAsync(R.xml.remote_config_defaults)

        config.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val adsResume = config.getBoolean(Constants.Ads_resume)

                val banner = config.getBoolean(Constants.Banner)
                val interSplash = config.getBoolean(Constants.Inter_Splash)
                val interSetup = config.getBoolean(Constants.Inter_Setup)
                val interVideoCall = config.getBoolean(Constants.Inter_VideoCall)
                val interChatVideoCall = config.getBoolean(Constants.Inter_Chat_VideoCall)
                val interOnboard = config.getBoolean(Constants.Inter_Onboard)

                val nativeLanguage = config.getBoolean(Constants.Native_Language)
                val nativeHome = config.getBoolean(Constants.Native_Home)
                val nativeSetupCall = config.getBoolean(Constants.Native_SetupCall)
                val nativeChooseSanta = config.getBoolean(Constants.Native_ChooseSanta)
                val nativeCountdown = config.getBoolean(Constants.Native_Countdown)
                val nativeOnboard = config.getBoolean(Constants.Native_Onboard)

                BasePrefers.getPrefsInstance().openResume = adsResume

                BasePrefers.getPrefsInstance().banner = banner
                BasePrefers.getPrefsInstance().interSplash = interSplash
                BasePrefers.getPrefsInstance().interSetup = interSetup
                BasePrefers.getPrefsInstance().interVideoCall = interVideoCall
                BasePrefers.getPrefsInstance().interChatVideoCall = interChatVideoCall
                BasePrefers.getPrefsInstance().interOnboard = interOnboard

                BasePrefers.getPrefsInstance().nativeLanguage = nativeLanguage
                BasePrefers.getPrefsInstance().nativeHome = nativeHome
                BasePrefers.getPrefsInstance().nativeSetupCall = nativeSetupCall
                BasePrefers.getPrefsInstance().nativeChooseSanta = nativeChooseSanta
                BasePrefers.getPrefsInstance().nativeCountdown = nativeCountdown
                BasePrefers.getPrefsInstance().nativeOnboard = nativeOnboard
            }
            if (!BasePrefers.getPrefsInstance().openResume) {
                AppOpenManager.getInstance().disableAppResumeWithActivity(MainActivity::class.java)
            } else {
                AppOpenManager.getInstance().enableAppResumeWithActivity(MainActivity::class.java)
                AppOpenManager.getInstance().enableAppResume()
            }
        }
    }

    private fun initRemoteConfig() {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {}
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
    }

    private fun initAdjust() {
        val adjustConfig = com.ads.control.config.AdjustConfig(Constants.adjust_token)
        aperoAdConfig?.adjustConfig = adjustConfig
    }

    private fun initAds() {
        val env =
            if (BuildConfig.build_debug) AperoAdConfig.ENVIRONMENT_DEVELOP else AperoAdConfig.ENVIRONMENT_PRODUCTION

        aperoAdConfig = AperoAdConfig(this, AperoAdConfig.PROVIDER_ADMOB, env)
        initAdjust()
        Admob.getInstance().setOpenActivityAfterShowInterAds(true)
        Admob.getInstance().setDisableAdResumeWhenClickAds(true)

        Admob.getInstance().setFan(true)
        Admob.getInstance().setAppLovin(true)
        Admob.getInstance().setColony(true)

        aperoAdConfig?.idAdResume = BuildConfig.Ads_resume

        AperoAd.getInstance().init(this, aperoAdConfig, false)
    }

    /** Setup Timber Log*/
    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            val logDebugTree = MyDebugTree(this, true)
            logDebugTree.deleteFileLogFolder(this)
            Timber.plant(logDebugTree)
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }
}
