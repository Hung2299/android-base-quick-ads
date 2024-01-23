package com.harison.core.app.ui.main

import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.harison.core.app.api.entity.ItemSoundEntity
import com.harison.core.app.utils.extensions.readAssetsFile
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Array
import java.lang.reflect.Type
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val data = MutableLiveData<ArrayList<ItemSoundEntity>>()
    fun parseJSON(jsonString: String) {
        val gson = Gson()
        val type: Type = object : TypeToken<List<ItemSoundEntity?>?>() {}.type
        val listSound: List<ItemSoundEntity> = gson.fromJson(jsonString, type)
        data.postValue(listSound as ArrayList<ItemSoundEntity>?)
        for (sound in listSound) {
            Timber.tag("----sound Details").d(sound.toString())
        }
    }
}
