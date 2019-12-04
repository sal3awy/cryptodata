package com.antonicastejon.cryptodata.domain.common

import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.parseError():T? {
    if (!isSuccessful) {
        throw HttpException(this)
    }
    return body()
}