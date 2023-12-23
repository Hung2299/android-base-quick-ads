package com.harison.core.app.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {
    var finishCountDown: () -> Unit = {}

    init {
        viewModelScope.launch {
            delay(5000L)
            finishCountDown.invoke()
        }
    }
}
