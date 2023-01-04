package com.xcod33.risfund

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_home.*
import okhttp3.OkHttpClient
import org.json.JSONException
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        initiate FAN
        AndroidNetworking.initialize(applicationContext)

        loginButton.setOnClickListener {
            login()
            Handler().postDelayed({
                homeData()
            }, 3000)
        }

        daftar2TextView.setOnClickListener {
            val intent = Intent(this, Register1Activity::class.java)
            startActivity(intent)
        }
    }

    fun homeData() {
            val sessionManager = SessionManager(this)
            val token = JSONObject(sessionManager.getToken())
            AndroidNetworking.get("https://79c9-125-160-101-0.ap.ngrok.io/api/user")
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.LOW)
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

                                    val bundle = Bundle()
                                    bundle.putString("userId", userId)
                                    bundle.putString("fullName", fullName)
                                    bundle.putString("phoneNumber", phoneNumber)
                                    bundle.putString("birthdate", birthdate)
                                    bundle.putString("gender", gender)
                                    bundle.putString("username", username)
                                    bundle.putString("balance", balance)
                                    bundle.putString("userQr", userQr)

                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
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
                                Toast.makeText(this@LoginActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
    }

    fun login() {
        val phoneNumber = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        val sessionManager = SessionManager(this)

        if (usernameEditText.text!!.isEmpty()) {
            usernameInputLayout.error = "Username is required"
        } else if (passwordEditText.text!!.isEmpty()) {
            passwordInputLayout.error = "Password is required"
        } else {
            val jobj = JSONObject()
            try {
                jobj.put("phoneNumber", phoneNumber)
                jobj.put("password", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://79c9-125-160-101-0.ap.ngrok.io/api/login")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if (response != null) {
                                if(response.getString("message").equals("Login Succeeded")) {
                                    Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_LONG).show()

                                    val token = JSONObject(response.getString("data"))

                                    sessionManager.setLogin(true)
                                    sessionManager.setToken(token.toString())
                                }
                            }
                        } catch (e: JSONException) {
                            Log.d("error", e.toString())
                        }
                    }

                    override fun onError(error: ANError?) {
                        if (error != null) {
                            if (error.errorCode != 0) {
                                val response = JSONObject(error.errorBody)
//                                Log.e("responseError", response.getString("message"))
                                Toast.makeText(this@LoginActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
        }
    }
}