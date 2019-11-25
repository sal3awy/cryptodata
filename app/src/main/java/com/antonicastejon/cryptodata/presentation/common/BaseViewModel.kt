package com.antonicastejon.cryptodata.presentation.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    internal val disposable: CompositeDisposable = CompositeDisposable()
    private val isLoading = MutableLiveData<Boolean>()
    private val error = MutableLiveData<Throwable>()

    internal fun setLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }

    internal fun setError(error: Throwable) {
        this.error.value = error
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    internal fun isLoading() = isLoading.toLiveData()

    internal fun error() = error.toLiveData()

}

fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> {
    return this
}