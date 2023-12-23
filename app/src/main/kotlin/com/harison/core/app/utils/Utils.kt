package com.harison.core.app.utils

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.harison.core.app.BuildConfig
import com.harison.core.app.utils.Constants.Inter_Splash
import com.harison.core.app.utils.Constants.Native_Language
import com.harison.core.app.utils.Constants.Native_Onboard
import timber.log.Timber

inline fun <reified T> T.logUtil(msg: Any) {
    Timber.e("----$msg")
}

fun isShowNativeLanguage(): Boolean {
    val config = FirebaseRemoteConfig.getInstance()
    config.fetch(0)
    return config.getBoolean(Native_Language)
}

fun isShowInterSplash(): Boolean {
    val config = FirebaseRemoteConfig.getInstance()
    config.fetch(0)
    return config.getBoolean(Inter_Splash)
}

fun isShowNativeOnboard(): Boolean {
    val config = FirebaseRemoteConfig.getInstance()
    config.fetch(0)
    return config.getBoolean(Native_Onboard)
}

fun isShowBanner(): Boolean {
    val config = FirebaseRemoteConfig.getInstance()
    config.fetch(0)
    return config.getBoolean(Constants.Banner)
}

fun isShowAppResume(): Boolean {
    val config = FirebaseRemoteConfig.getInstance()
    config.fetch(0)
    return config.getBoolean(Constants.Ads_resume)
}

fun isShowInterOnboard(): Boolean {
    val config = FirebaseRemoteConfig.getInstance()
    config.fetch(0)
    return config.getBoolean(Constants.Inter_Onboard)
}

