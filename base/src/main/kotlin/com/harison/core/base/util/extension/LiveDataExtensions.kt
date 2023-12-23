package com.harison.core.base.util.extension

import androidx.lifecycle.LiveData
import com.harison.core.base.ui.NonNullMediatorLiveData

fun <T> LiveData<T>.nonNull(): NonNullMediatorLiveData<T> {
    val mediator: NonNullMediatorLiveData<T> = NonNullMediatorLiveData()
    mediator.addSource(this) { t ->
        t?.let {
            mediator.value = it
        }
    }

    return mediator
}

