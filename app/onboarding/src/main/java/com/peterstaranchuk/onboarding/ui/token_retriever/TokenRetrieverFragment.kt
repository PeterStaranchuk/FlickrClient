package com.peterstaranchuk.onboarding.ui.token_retriever

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.peterstaranchuk.common.BaseFragment
import com.peterstaranchuk.onboarding.ui.AuthDependency
import com.peterstaranchuk.onboarding.ui.AuthService
import com.peterstaranchuk.onboarding.ui.authModule
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class TokenRetrieverFragment : BaseFragment() {
    override val dependenciesModule = listOf(tokenRetrieverModule, authModule)

    private val vm: TokenRetrieverViewModel by inject()
    private val scope by lazy { getKoin().getOrCreateScope(AuthDependency.SCOPE_ID, named(AuthDependency.SCOPE_NAME)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authService = scope.get<AuthService>()
        vm.provideAuthService(authService)
    }

    override fun onDestroy() {
        scope.close()
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ProgressState()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.onAuthFinished(activity?.intent?.dataString.toString())
    }

    @Composable
    fun ProgressState() {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
            )
        }
    }

}