package com.peterstaranchuk.onboarding.ui.auth

import android.net.Uri
import android.util.Base64
import com.peterstaranchuk.common.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.UnsupportedEncodingException
import java.lang.IllegalStateException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

interface AuthInteractor {
    fun getAuthUrl() : Flow<String>
}

class AuthInteractorImpl(private val authApi : AuthApi) : AuthInteractor {

    override fun getAuthUrl(): Flow<String> {
        return flow {
            val consumerSecret = "188ac244748e11d3"
            val consumerKey = "6ede91d0033fe6a0b4b5702e39108a86"
            val signatureMethod = "HMAC-SHA1"
            val oauthVersion = "1.0"
            val timestamp = System.currentTimeMillis()
            val oauthNonce = UUID.randomUUID().toString()
            val oauthCallback = "https://www.resultcallback.com/success"

            val uri = Uri.Builder()
                .path(BuildConfig.BASE_URL)
                .appendPath("services/oauth/request_token")
                .appendQueryParameter("oauth_callback", oauthCallback)
                .appendQueryParameter("oauth_consumer_key", consumerKey)
                .appendQueryParameter("oauth_nonce", oauthNonce)
                .appendQueryParameter("oauth_signature_method", signatureMethod)
                .appendQueryParameter("oauth_timestamp", timestamp.toString())
                .appendQueryParameter("oauth_version", oauthVersion)
                .build()
            val baseString = "GET&$uri"

            val oauthSignature = computeHmac(baseString, consumerSecret)

            val requestToken = authApi.requestToken(
                consumerKey = consumerKey,
                signatureMethod = signatureMethod,
                timestamp = timestamp,
                oauthVersion = oauthVersion,
                oauthNonce = oauthNonce,
                oauthCallback = oauthCallback,
                oauthSignature = oauthSignature
            )
            emit("www.google.com") //todo replace by actual
        }.flowOn(Dispatchers.IO)
    }

    suspend fun computeHmac(baseString: String, key: String): String {
        val mac: Mac = Mac.getInstance("HmacSHA1")
        val secret = SecretKeySpec(key.toByteArray(), mac.algorithm)
        mac.init(secret)
        val digest: ByteArray = mac.doFinal(baseString.toByteArray())
        val result: ByteArray = Base64.encode(digest, Base64.DEFAULT)
        return String(result)
    }

}