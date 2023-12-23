package com.harison.core.app.utils.extensions

import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

fun View.setGradientBackground(
    startColor: Int,
    endColor: Int,
    orientation: GradientDrawable.Orientation? = GradientDrawable.Orientation.TOP_BOTTOM
) {
    val gradientDrawable = GradientDrawable(
        orientation,
        intArrayOf(
            ContextCompat.getColor(context, startColor),
            ContextCompat.getColor(context, endColor)
        )
    )
    background = gradientDrawable
}

fun TextView.setTextColorGradient(
    startColor: Int,
    endColor: Int,
    orientation: Shader.TileMode = Shader.TileMode.CLAMP
) {
    val textShader = LinearGradient(
        0f, 0f, 0f, textSize,
        intArrayOf(startColor, endColor),
        null, orientation
    )
    paint.shader = textShader
}