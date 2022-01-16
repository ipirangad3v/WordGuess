package com.ipsoft.wordguess.domain.core.extension

import android.view.View
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.findViewTreeLifecycleOwner
import kotlinx.coroutines.*
import java.util.*

fun View.delayOnLifeCycle(
    durationInMillis: Long,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: () -> Unit
): Job? = findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
    lifecycleOwner.lifecycle.coroutineScope.launch(dispatcher) {
        delay(durationInMillis)
        block()
    }
}

fun View.getNamedId(): String {
    return when {
        this.resources.getResourceName(this.id).contains("del") -> "del"
        this.resources.getResourceName(this.id).contains("enter") -> "enter"
        this.id == View.NO_ID -> "no-id"
        else -> this.resources.getResourceName(this.id).last().toString()
            .uppercase(Locale.getDefault())
    }
}