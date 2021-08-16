package com.peterstaranchuk.common

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.error.DefinitionOverrideException
import org.koin.core.module.Module
import timber.log.Timber

abstract class BaseFragment : Fragment() {

    abstract val dependenciesModule : Module

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            loadKoinModules(dependenciesModule)
        } catch (e : DefinitionOverrideException) {
            Timber.e(e)
        }
    }

    override fun onDestroy() {
        unloadKoinModules(dependenciesModule)
        super.onDestroy()
    }
}