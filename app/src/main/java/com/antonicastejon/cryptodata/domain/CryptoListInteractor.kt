package com.antonicastejon.cryptodata.domain

import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import com.antonicastejon.cryptodata.model.Crypto
import com.antonicastejon.cryptodata.model.coinMarketCapRepositoryDep

const val LIMIT_CRYPTO_LIST = 20


class CryptoListInteractor(private val coinMarketCapRepository: CoinMarketCapRepository = coinMarketCapRepositoryDep) : CryptoListUseCases {

    override suspend fun getCryptoListBy(page: Int): List<CryptoViewModel>? {
        return coinMarketCapRepository.getCryptoList(page, LIMIT_CRYPTO_LIST).body()?.map { cryptoViewModelMapper(it) }
    }

    private val cryptoViewModelMapper: (Crypto) -> CryptoViewModel = { crypto ->
        CryptoViewModel(crypto.id, crypto.name, crypto.symbol, crypto.rank, crypto.priceUsd.toFloat(), crypto.priceBtc.toFloat(), crypto.percentChange24h.toFloat())
    }
}