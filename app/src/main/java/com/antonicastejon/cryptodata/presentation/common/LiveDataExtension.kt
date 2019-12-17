package com.antonicastejon.cryptodata.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> {
    return this
}