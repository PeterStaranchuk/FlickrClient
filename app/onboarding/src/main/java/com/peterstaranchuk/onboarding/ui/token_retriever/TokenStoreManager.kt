package com.peterstaranchuk.common

import android.content.SharedPreferences

interface TokenStoreManager {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}

class TokenStoreManagerImpl(private val sharedPreferences: SharedPreferences) : TokenStoreManager {

    companion object {
        const val TOKEN = "com.peterstaranchuk.common.TOKEN"
    }


    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN, token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    override fun clearToken() {
        sharedPreferences.edit().remove(TOKEN).apply()
    }


}