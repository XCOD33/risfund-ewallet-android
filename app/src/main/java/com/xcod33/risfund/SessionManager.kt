package com.xcod33.risfund

import android.content.Context
import android.content.SharedPreferences

class SessionManager(var context: Context?) {
    private val IS_LOGIN = "is_login"

    var pref: SharedPreferences? = context?.getSharedPreferences("GET_TOKEN", Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor? = pref?.edit()

    fun setLogin(isLogin: Boolean) {
        editor?.putBoolean(IS_LOGIN, isLogin)
    }

    fun setToken(token: String) {
        editor?.putString("token", token)
        editor?.commit()
    }

    fun getToken(): String? {
        return pref?.getString("token", "")
    }
}