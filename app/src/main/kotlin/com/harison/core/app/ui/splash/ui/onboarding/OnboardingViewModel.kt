package com.harison.core.app.ui.splash.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {

    var num = 0
    fun test() {
        viewModelScope.launch {
            repeat(1000) {
                delay(1000)
                num = it
                Timber.e("----num share parent: $num")
            }
        }
    }
}