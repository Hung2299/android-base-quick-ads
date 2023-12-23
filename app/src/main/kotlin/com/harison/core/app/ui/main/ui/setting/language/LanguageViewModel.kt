package com.harison.core.app.ui.main.ui.setting.language

import androidx.lifecycle.ViewModel
import com.harison.core.app.ui.splash.ui.first_language.Language
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(): ViewModel() {
    var chosenLanguage: Language? = null
}