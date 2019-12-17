package com.antonicastejon.cryptodata.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.antonicastejon.cryptodata.R
import com.antonicastejon.cryptodata.presentation.common.replaceFragment
import com.antonicastejon.cryptodata.presentation.main.crypto_list.CRYPTO_LIST_FRAGMENT_TAG
import com.antonicastejon.cryptodata.presentation.main.crypto_list.newCryptoListFragment

class MainActivity : AppCompatActivity() {

    private val cryptoListFragment by lazy { newCryptoListFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) replaceFragment(R.id.container, cryptoListFragment, CRYPTO_LIST_FRAGMENT_TAG)
    }
}
