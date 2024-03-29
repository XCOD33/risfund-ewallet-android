package com.xcod33.risfund

import android.content.Context
import android.content.SharedPreferences

class SessionManager(var context: Context) {
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

    fun clearToken() {
        editor?.clear()
        editor?.commit()
    }

    fun setPayment(code: String?, name: String?, icon_url: String?) {
        editor?.putString("code", code)
        editor?.putString("name", name)
        editor?.putString("iconUrl", icon_url)
        editor?.commit()
    }

    fun getPayment(): HashMap<String, String?> {
        val method = HashMap<String, String?>()
        method["code"] = pref?.getString("code", "BCAVA")
        method["name"] = pref?.getString("name", "BCA Virtual Account")
        method["iconUrl"] = pref?.getString("iconUrl", "https://tripay.co.id/images/payment-channel/ytBKvaleGy1605201833.png")
        return method
    }
}