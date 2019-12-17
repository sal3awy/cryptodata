package com.antonicastejon.cryptodata.presentation.common

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    internal val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}