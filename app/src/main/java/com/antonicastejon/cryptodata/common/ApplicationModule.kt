package com.antonicastejon.cryptodata.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

val dispatcherMainDep: CoroutineDispatcher = Dispatchers.Main

val dispatcherIoDep: CoroutineDispatcher = Dispatchers.IO

val dispatcherDefaultDep: CoroutineDispatcher = Dispatchers.Default
