package com.harison.core.app.utils.extensions

import android.content.res.AssetManager
fun AssetManager.readAssetsFile(fileName : String): String = open(fileName).bufferedReader().use{it.readText()}
