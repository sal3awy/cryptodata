package com.antonicastejon.cryptodata.presentation.main.crypto_list

import androidx.lifecycle.MutableLiveData
import com.antonicastejon.cryptodata.domain.common.addTo
import com.antonicastejon.cryptodata.presentation.common.androidMainThreadScheduler
import com.antonicastejon.cryptodata.presentation.common.schedulerIo
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import com.antonicastejon.cryptodata.domain.LIMIT_CRYPTO_LIST
import com.antonicastejon.cryptodata.domain.cryptoListUseCasesDep
import com.antonicastejon.cryptodata.presentation.common.*
import io.reactivex.Scheduler


private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel(private val cryptoListUseCases: CryptoListUseCases = cryptoListUseCasesDep, private val subscribeOnScheduler: Scheduler = schedulerIo, private val observeOnScheduler: Scheduler = androidMainThreadScheduler) : BaseViewModel() {

    val stateLiveData = MutableLiveData<ViewStateWithPagination<List<CryptoViewModel>>>()

    init {
        stateLiveData.value = DefaultPageState(0, false, emptyList())
    }

    fun updateCryptoList() {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = if (pageNum == 0)
            LoadingPageState(pageNum, false, obtainCurrentData())
        else
            PaginatingState(pageNum, false, obtainCurrentData())
        getCryptoList(pageNum)
    }

    fun resetCryptoList() {
        stateLiveData.value = LoadingPageState(0, false, emptyList())
        updateCryptoList()
    }

    fun restoreCryptoList() {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = DefaultPageState(pageNum, false, obtainCurrentData())
    }

    private fun getCryptoList(page: Int) {
        cryptoListUseCases.getCryptoListBy(page)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(this::onCryptoListReceived, this::onError).addTo(disposable)
    }

    private fun onCryptoListReceived(cryptoList: List<CryptoViewModel>) {
        val currentCryptoList = obtainCurrentData().toMutableList()
        val currentPageNum = obtainCurrentPageNum() + 1
        val areAllItemsLoaded = cryptoList.size < LIMIT_CRYPTO_LIST
        currentCryptoList.addAll(cryptoList)
        stateLiveData.value = DefaultPageState(currentPageNum, areAllItemsLoaded, currentCryptoList)
    }

    private fun onError(error: Throwable) {
        val pageNum = stateLiveData.value?.pageNum ?: 0
        stateLiveData.value = ErrorPageState(error, pageNum, obtainCurrentLoadedAllItems(), obtainCurrentData())
    }

    private fun obtainCurrentPageNum() = stateLiveData.value?.pageNum ?: 0

    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()

    private fun obtainCurrentLoadedAllItems() = stateLiveData.value?.loadedAllItems ?: false

}