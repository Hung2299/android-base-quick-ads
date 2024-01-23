package com.harison.core.app.domain

import com.harison.core.app.utils.sampleName
import com.harison.core.app.utils.sampleSoundURL

data class ItemSound(
    val id: String = "0",
    val soundUrl: String = sampleSoundURL,
    val name: String = sampleName,
    var isSelect: Boolean = false,
    val isHot: Boolean = false
)
