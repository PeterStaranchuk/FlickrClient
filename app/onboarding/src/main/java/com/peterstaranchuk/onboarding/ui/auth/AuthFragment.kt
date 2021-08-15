package com.peterstaranchuk.onboarding.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Observer
import com.peterstaranchuk.common.BaseFragment
import com.peterstaranchuk.onboarding.R
import org.koin.android.ext.android.inject

class AuthFragment : BaseFragment() {

    private val vm: AuthViewModel by inject()
    override val dependenciesModule = authModule
    private val authNavigator : AuthNavigator by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = vm.state.collectAsState()
                when(state.value) {
                    is AuthContract.State.Auth -> {
                        Column {
                            Header()
                            WebViewPart()
                        }
                    }
                    AuthContract.State.Loading -> {
                        LoadingStateComposable()
                    }
                    AuthContract.State.RedirectToNextScreen -> {
                        //todo redirect
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.fetchAuthUrl()

        vm.screenEvent.observe(viewLifecycleOwner, Observer {
            when(it) {
                AuthContract.Event.CloseScreen -> authNavigator.goBack(this@AuthFragment)
            }
        })
    }

    @Composable
    private fun WebViewPart() {
        val context = LocalContext.current
        AndroidView(
            factory = {
                WebView(context).apply {
                    this.loadUrl("https://www.google.com")

                }
            },
            modifier = Modifier.fillMaxSize(),
            update = {

            })
    }

    @Composable
    fun LoadingStateComposable() {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
            )
        }
    }

    @Composable
    private fun Header() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                tint= Color.White,
                contentDescription = stringResource(R.string.auth_screen_close_button_content_description),
                modifier = Modifier
                    .clickable { vm.onCloseIconClicked() }
                    .padding(all = 4.dp)
            )
        }
    }

    @Preview
    @Composable
    fun Preview() {
        Header()
    }
}