package com.peterstaranchuk.onboarding.ui

import android.net.Uri
import com.github.scribejava.apis.FlickrApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.peterstaranchuk.onboarding.BuildConfig

interface AuthService {
    suspend fun getAuthUrl(): String
    suspend fun getAccessToken(deeplink: String): String
}

class AuthServiceImpl : AuthService {
    val oauthCallback = "deeplink://authorization"

    private val service by lazy {
        ServiceBuilder(BuildConfig.FLICKR_API_KEY)
            .apiSecret(BuildConfig.FLICKR_API_SECRET)
            .callback(oauthCallback)
            .build(FlickrApi.instance(FlickrApi.FlickrPerm.DELETE))
    }

    private val requestToken by lazy {
        service.requestToken
    }

    override suspend fun getAuthUrl(): String {
        return service.getAuthorizationUrl(requestToken)
    }

    override suspend fun getAccessToken(deeplink: String): String {
        val verifier = Uri.parse(deeplink).getQueryParameter("oauth_verifier")
        val accessToken = service.getAccessToken(requestToken, verifier)
        return accessToken.token
    }

}