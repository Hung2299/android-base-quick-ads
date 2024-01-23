package com.harison.core.app.api.entity

data class ItemSoundEntity(
    val id: String,
    val name: String,
    val sound_url: String,
    val isHot: String? = null
)
