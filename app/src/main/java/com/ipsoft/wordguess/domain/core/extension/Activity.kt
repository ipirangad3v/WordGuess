package com.ipsoft.wordguess.domain.core.extension

import android.app.Activity
import android.graphics.Point

fun Activity.isSmallScreen(): Boolean {

    val display = this.windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    val width = size.x
    size.y
    return width <= 720
}