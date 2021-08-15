package com.peterstaranchuk.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

abstract class BaseFragment : Fragment() {

    abstract val dependenciesModule : Module

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(dependenciesModule)
    }

    override fun onDestroy() {
        unloadKoinModules(dependenciesModule)
        super.onDestroy()
    }
}