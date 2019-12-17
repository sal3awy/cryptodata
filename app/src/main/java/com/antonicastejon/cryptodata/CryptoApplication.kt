package com.antonicastejon.cryptodata

import android.app.Application
import com.antonicastejon.cryptodata.model.common.ApplicationIntegration

class CryptoApplication : Application() {

    override fun onCreate() {
        ApplicationIntegration.with(this)
        super.onCreate()
    }
}