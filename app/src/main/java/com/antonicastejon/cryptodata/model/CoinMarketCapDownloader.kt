package com.antonicastejon.cryptodata.model

import retrofit2.Response

class CoinMarketCapDownloader(private val coinMarketCapApi: CoinMarketCapApi = coinMarketCapApiDep) : CoinMarketCapRepository {
    override suspend fun getCryptoList(page: Int, limit: Int): Response<List<Crypto>> = coinMarketCapApi.getCryptoList(page * limit, limit)
}