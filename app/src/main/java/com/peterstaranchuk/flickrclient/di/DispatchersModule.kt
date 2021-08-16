package com.peterstaranchuk.flickrclient.di

import com.peterstaranchuk.common.DispatchersProvider
import com.peterstaranchuk.common.DispatchersProviderImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dispatchersModule = module {

    factory(named("dispatcher_io")) { Dispatchers.IO }
    factory(named("dispatcher_main")) { Dispatchers.Main }
    factory<DispatchersProvider> { DispatchersProviderImpl(io = get(named("dispatcher_io")), main = get(named("dispatcher_main"))) }
}