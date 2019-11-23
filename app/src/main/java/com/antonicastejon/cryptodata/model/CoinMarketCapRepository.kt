package com.antonicastejon.cryptodata.model

import io.reactivex.Single

val coinMarketCapRepositoryDep by lazy {
    CoinMarketCapDownloader()
}

interface CoinMarketCapRepository {
    fun getCryptoList(page: Int, limit: Int): Single<List<Crypto>>
}