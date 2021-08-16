package com.peterstaranchuk.onboarding.ui.auth

import com.github.scribejava.apis.FlickrApi
import com.github.scribejava.core.builder.ServiceBuilder
import com.peterstaranchuk.onboarding.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface OnboardingInteractor {
    fun getAuthUrl(): Flow<String>
}

class OnboardingInteractorImpl : OnboardingInteractor {

    override fun getAuthUrl(): Flow<String> {
        return flow {
            val oauthCallback = "deeplink://authorization"

            val service = ServiceBuilder(BuildConfig.FLICKR_API_KEY)
                .apiSecret(BuildConfig.FLICKR_API_SECRET)
                .callback(oauthCallback)
                .build(FlickrApi.instance(FlickrApi.FlickrPerm.DELETE));

            val requestToken = service.requestToken;

            val authorizationUrl = service.getAuthorizationUrl(requestToken);
            emit(authorizationUrl)
        }.flowOn(Dispatchers.IO)
    }

}