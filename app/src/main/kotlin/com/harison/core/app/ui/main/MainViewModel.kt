package com.harison.core.app.ui.main

import androidx.lifecycle.ViewModel
import com.harison.core.app.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    init {

    }
}
