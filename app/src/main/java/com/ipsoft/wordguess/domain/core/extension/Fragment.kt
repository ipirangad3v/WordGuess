package com.ipsoft.wordguess.domain.core.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navTo(@IdRes destination: Int) {

    this.findNavController().navigate(destination)

}
