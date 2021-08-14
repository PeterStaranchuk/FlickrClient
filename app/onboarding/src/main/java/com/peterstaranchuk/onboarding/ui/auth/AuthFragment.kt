package com.peterstaranchuk.onboarding.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment

class AuthFragment : Fragment() {
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = WebView(requireContext())
        view.settings.javaScriptEnabled = true
        view.settings.loadWithOverviewMode = true
        view.settings.javaScriptCanOpenWindowsAutomatically = true
        val url= "www.flickr.com"
        view.loadUrl(url)
        return view
    }
}