package com.antonicastejon.cryptodata.presentation.main.crypto_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonicastejon.cryptodata.common.dispatcherIoDep
import com.antonicastejon.cryptodata.common.dispatcherMainDep
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import com.antonicastejon.cryptodata.domain.LIMIT_CRYPTO_LIST
import com.antonicastejon.cryptodata.domain.cryptoListUseCasesDep
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel(private val cryptoListUseCases: CryptoListUseCases = cryptoListUseCasesDep, private val dispatcherIo: CoroutineDispatcher = dispatcherIoDep, private val dispatcherMain: CoroutineDispatcher = dispatcherMainDep) : ViewModel() {

    val stateLiveData = MutableLiveData<CryptoListState>()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch(dispatcherMain) {
            onError(throwable)
        }
    }

    init {
        stateLiveData.value = DefaultState(0, false, emptyList())
    }

    fun updateCryptoList() {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = if (pageNum == 0)
            LoadingState(pageNum, false, obtainCurrentData())
        else
            PaginatingState(pageNum, false, obtainCurrentData())
        getCryptoList(pageNum)
    }

    fun resetCryptoList() {
        stateLiveData.value = LoadingState(0, false, emptyList())
        updateCryptoList()
    }

    fun restoreCryptoList() {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = DefaultState(pageNum, false, obtainCurrentData())
    }

    private fun getCryptoList(page: Int) {
        viewModelScope.launch(dispatcherIo + coroutineExceptionHandler) {
            cryptoListUseCases.getCryptoListBy(page)?.let {
                withContext(dispatcherMain) {
                    onCryptoListReceived(it)
                }
            }
        }
    }

    private fun onCryptoListReceived(cryptoList: List<CryptoViewModel>) {
        val currentCryptoList = obtainCurrentData().toMutableList()
        val currentPageNum = obtainCurrentPageNum() + 1
        val areAllItemsLoaded = cryptoList.size < LIMIT_CRYPTO_LIST
        currentCryptoList.addAll(cryptoList)
        stateLiveData.value = DefaultState(currentPageNum, areAllItemsLoaded, currentCryptoList)
    }

    private fun onError(error: Throwable) {
        val pageNum = stateLiveData.value?.pageNum ?: 0
        stateLiveData.value = ErrorState(error, pageNum, obtainCurrentLoadedAllItems(), obtainCurrentData())
    }

    private fun obtainCurrentPageNum() = stateLiveData.value?.pageNum ?: 0

    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()

    private fun obtainCurrentLoadedAllItems() = stateLiveData.value?.loadedAllItems ?: false

}