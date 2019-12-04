package com.antonicastejon.cryptodata.use_cases

import com.antonicastejon.cryptodata.common.cryptoPOJOmodel
import com.antonicastejon.cryptodata.common.cryptoViewModelFrom
import com.antonicastejon.cryptodata.common.mock
import com.antonicastejon.cryptodata.common.whenever
import com.antonicastejon.cryptodata.domain.CryptoListInteractor
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import kotlinx.coroutines.coroutineScope
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt

/**
 * Created by Antoni Castejón
 * 28/01/2018.
 */
class CryptoListUseCasesUnitTest {

   /* val coinMarketCapRepository = mock<CoinMarketCapRepository>()

    val cryptoListUseCases by lazy { CryptoListInteractor(coinMarketCapRepository) }

    @Test
    fun testCryptoListUseCases_getCryptoList_Completed() {
        coroutineScope {

        }
        whenever(coinMarketCapRepository.getCryptoList(anyInt(), anyInt()))
                .thenReturn(Single.just(emptyList()))

        cryptoListUseCases.getCryptoListBy(0)
                .test()
                .assertComplete()
    }

    @Test
    fun testCryptoListUseCases_getCryptoList_Error() {
        val response = Throwable("Error response")
        whenever(coinMarketCapRepository.getCryptoList(anyInt(), anyInt()))
                .thenReturn(Single.error(response))

        cryptoListUseCases.getCryptoListBy(0)
                .test()
                .assertError(response)

    }

    @Test
    fun testCryptoListUseCases_getCryptoList_response() {
        val response = arrayListOf(cryptoPOJOmodel())
        whenever(coinMarketCapRepository.getCryptoList(anyInt(), anyInt()))
                .thenReturn(Single.just(response))

        val expectedList = arrayListOf(cryptoViewModelFrom(cryptoPOJOmodel()))

        cryptoListUseCases.getCryptoListBy(0)
                .test()
                .assertValue(expectedList)
    }*/
}