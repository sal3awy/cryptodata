package com.antonicastejon.cryptodata.model

import retrofit2.Response

val coinMarketCapRepositoryDep by lazy {
    CoinMarketCapDownloader()
}

interface CoinMarketCapRepository {
    suspend fun getCryptoList(page: Int, limit: Int): Response<List<Crypto>>
}