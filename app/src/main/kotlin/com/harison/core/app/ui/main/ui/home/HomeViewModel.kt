package com.harison.core.app.ui.main.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harison.core.app.database.dao.SampleDao
import com.harison.core.app.database.entity.SampleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(private val sampleDao: SampleDao) : ViewModel() {
    fun insertSample(){
        viewModelScope.launch {
            sampleDao.insert(
                SampleEntity(
                    1,"abc","def","name_test"
                )
            )
        }
    }
}