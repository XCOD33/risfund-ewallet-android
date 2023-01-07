package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val userList = ArrayList<GetUserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set a delay for the splash screen
        val splashDelay: Long = 3000 // 3 seconds

        Handler().postDelayed({
            val sessionManager = SessionManager(this)
            if(sessionManager.getToken()!!.isNotEmpty()) {
                checkLogin()
            } else {
            // Launch the main activity
            sessionManager.clearToken()
            startActivity(Intent(this, LoginActivity::class.java))
            // Close the splash screen activity
            finish()
            }

        }, splashDelay)
    }

    private fun checkLogin() {
        val sessionManager = SessionManager(this)
            val token = JSONObject(sessionManager.getToken())
            AndroidNetworking.get("https://risfund.loophole.site/api/user")
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if (response != null) {
                                if(response.getString("message").equals("User found")) {
                                    val data = JSONObject(response.getString("data"))

                                    Log.d("token", token.getString("token"))

                                    val userId = data.getString("userId")
                                    val fullName = data.getString("fullName")
                                    val phoneNumber = data.getString("phoneNumber")
                                    val birthdate = data.getString("birthdate")
                                    val gender = data.getString("gender")
                                    val username = data.getString("username")
                                    val balance = data.getString("balance")
                                    val userQr = data.getString("userQr")

                                    val responses = GetUserResponse(userId.toInt(), fullName, phoneNumber, birthdate, gender, username, balance.toInt(), userQr)

                                    userList.add(responses)

                                    val intent = Intent(this@MainActivity, HomeActivity2::class.java)
                                    intent.putExtra("dataUser", responses)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        } catch (error: JSONException) {
                            Log.d("error response", error.toString())
                        }
                    }

                    override fun onError(error: ANError?) {
                        if (error != null) {
                            if (error.errorCode != 0) {
                                val response = JSONObject(error.errorBody)
//                                Log.e("responseError", response.getString("message"))
                                Toast.makeText(this@MainActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                                sessionManager.clearToken()
                            }
                        }
                    }
                })
    }
}
