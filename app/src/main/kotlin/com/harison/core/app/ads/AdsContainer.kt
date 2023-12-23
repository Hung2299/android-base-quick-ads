package com.harison.core.app.ads

import com.ads.control.ads.wrapper.ApInterstitialAd
import com.ads.control.ads.wrapper.ApRewardAd
import javax.inject.Inject
import javax.inject.Singleton
import timber.log.Timber


/**
 * this singleton will be used as a container for [ApInterstitialAd] and [ApRewardAd].
 * Ads will be stored in a [MutableMap] with the key is the ad's id and the value will be the ad associated with the key provided
 * @author Duc An
 */
@Singleton
class AdsContainer @Inject constructor() {
    private val interAdMap = mutableMapOf<String, ApInterstitialAd?>()
    private val rewardAdMap = mutableMapOf<String, ApRewardAd?>()
    private var lastTimeShownInterAd = 0L
    private var interCoolDownInMillis = 30000
    @Synchronized
    fun getInterAd(adId: String): ApInterstitialAd? {
        val canShowInterAd =
            System.currentTimeMillis() - lastTimeShownInterAd > interCoolDownInMillis
        if (canShowInterAd) {
            lastTimeShownInterAd = System.currentTimeMillis()
        } else {
            Timber.tag(this.javaClass.simpleName).d("Interstitial ad is cooling down")
        }
        return if (canShowInterAd) interAdMap[adId] else null
    }

    @Synchronized
    fun isInterAdReady(adId: String): Boolean {
        val interAd = interAdMap[adId]
        return interAd != null && interAd.isReady
    }

    @Synchronized
    fun saveInterAd(adId: String, interAd: ApInterstitialAd?) {
        if (!isInterAdReady(adId)) {
            interAdMap[adId] = interAd
            Timber.tag(this.javaClass.simpleName)
                .d("An Interstitial ad for id '$adId' is stored")
        } else {
            Timber.tag(this.javaClass.simpleName)
                .w("Interstitial ad for id '$adId' has already existed")
        }
    }

    @Synchronized
    fun getRewardedAd(adId: String): ApRewardAd? {
        return rewardAdMap[adId]
    }

    @Synchronized
    fun isRewardedAdReady(adId: String): Boolean {
        val rewardAd = rewardAdMap[adId]
        return rewardAd != null && rewardAd.isReady
    }

    @Synchronized
    fun removeRewardedAd(adId: String) {
        rewardAdMap.remove(adId)
    }

    @Synchronized
    fun saveRewardedAd(adId: String, rewardAd: ApRewardAd?) {
        if (!isRewardedAdReady(adId)) {
            rewardAdMap[adId] = rewardAd
            Timber.tag(this.javaClass.simpleName).d("A Rewarded ad for id '$adId' is stored")
        } else {
            Timber.tag(this.javaClass.simpleName)
                .w("Rewarded ad for id '$adId' has already existed")
        }
    }
}
