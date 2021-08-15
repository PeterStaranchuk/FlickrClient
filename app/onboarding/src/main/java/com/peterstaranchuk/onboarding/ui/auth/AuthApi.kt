package com.peterstaranchuk.onboarding.ui.auth

import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {

    @GET("services/oauth/request_token")
    suspend fun requestToken(
        @Query("oauth_consumer_key") consumerKey : String,
        @Query("oauth_signature_method") signatureMethod : String,
        @Query("oauth_timestamp") timestamp : Long,
        @Query("oauth_nonce") oauthNonce : String,
        @Query("oauth_version") oauthVersion : String,
        @Query("oauth_callback") oauthCallback : String,
        @Query("oauth_signature") oauthSignature : String
    ) : String
}