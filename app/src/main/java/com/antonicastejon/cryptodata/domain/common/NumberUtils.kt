package com.antonicastejon.cryptodata.domain.common


fun Float.formatTo(numberOfDecimals: Int) =
        String.format("%.${numberOfDecimals}f", this)