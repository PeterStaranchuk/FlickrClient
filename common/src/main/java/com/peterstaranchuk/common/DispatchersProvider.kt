package com.peterstaranchuk.common

import kotlin.coroutines.CoroutineContext

//Unfortunately Kotlin team still doesn't release the way to replace Dispathers.Main during the test https://github.com/Kotlin/kotlinx.coroutines/issues/982
//so the current approach is to inject dispatchers to have the full control during unit tests
interface DispatchersProvider {
    val io: CoroutineContext
    val main: CoroutineContext
}

class DispatchersProviderImpl(override val io: CoroutineContext, override val main: CoroutineContext) : DispatchersProvider