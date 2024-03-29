package com.xcod33.risfund

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import org.json.JSONException
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private val userList = ArrayList<GetUserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sessionManager = SessionManager(this)
        if(sessionManager.getToken()!!.isNotEmpty()) {
            val token = JSONObject(sessionManager.getToken())
            if(token.getString("token").isNotEmpty()) {
                homeData()
            }
        }


        loginButton.setOnClickListener {
            login()
//            Handler().postDelayed({
//                homeData()
//            }, 3000)
        }

        requestOtpButton.setOnClickListener {
            requestOtpButton.isEnabled = false
            getOtp()
            val timer = object : CountDownTimer(30000, 10) {
                override fun onTick(millisUntilFinished: Long) {
                    requestOtpButton.setBackgroundColor(Color.GRAY)

                    val second = millisUntilFinished / 1000
                    countdownOtpTextView.visibility = View.VISIBLE
                    countdownOtpTextView.text = "Tunggu hingga ${second} detik untuk mencoba kembali"
                }
                override fun onFinish() {
                    countdownOtpTextView.visibility = View.GONE
//                    Toast.makeText(this@LoginActivity, "OTP tidak masuk? minta OTP lagi", Toast.LENGTH_SHORT).show()
                    requestOtpButton.isEnabled = true
                    requestOtpButton.setBackgroundColor(Color.parseColor("#25aa63"))
                }
            }
            timer.start()
        }

        daftar2TextView.setOnClickListener {
            val intent = Intent(this, Register1Activity::class.java)
            startActivity(intent)
        }
    }

    private fun getOtp() {
        if(usernameEditText.text.isNullOrEmpty()) {
            usernameEditText.error = "Harap masukkan nomor handphone"
            usernameEditText.requestFocus()
        } else {
            Toast.makeText(this, "OTP Dikirim", Toast.LENGTH_LONG).show()

            val jobj = JSONObject()
            try {
                jobj.put("phoneNumber", usernameEditText.text.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://risfund.loophole.site/api/get-otp")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if(response!!.getString("message").equals("otp sent")) {
                                Log.d("otp","OTP Sudah diterima")
                            }
                        } catch (e: JSONException) {
                            Log.d("eREsponse", e.toString())
                        }
                    }

                    override fun onError(anError: ANError?) {
                        if(anError!!.errorCode != 0) {
                            val response = JSONObject(anError.errorBody)

                            Toast.makeText(this@LoginActivity, response.getString("data"), Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }

    }

    fun homeData() {
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())
        Log.d("token", token.getString("token"))
        AndroidNetworking.get("https://risfund.loophole.site/api/user")
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

                                userList.clear()

                                val responses = GetUserResponse(userId.toInt(), fullName, phoneNumber, birthdate, gender, username, balance.toInt(), userQr)

                                userList.add(responses)

                                val intent = Intent(this@LoginActivity, HomeActivity2::class.java)
                                intent.putExtra("dataUser", responses)
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
                            startActivity(Intent(this@LoginActivity, LoginActivity::class.java))
                        }
                    }
                }
            })
    }

    private fun login() {
        val sessionManager = SessionManager(this)

        val phoneNumber = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val otp = otpEditText.text.toString().trim()

        if (usernameEditText.text!!.isEmpty()) {
            usernameEditText.error = "Username is required"
            usernameEditText.requestFocus()
        } else if (passwordEditText.text!!.isEmpty()) {
            passwordEditText.error = "Password is required"
            passwordEditText.requestFocus()
        } else {

            if(otp.isEmpty()) {
                otpEditText.error = "Harap Masukkan OTP"
                otpEditText.isFocusable = true
            } else {
                val jobj = JSONObject()
                try {
                    jobj.put("phoneNumber", phoneNumber)
                    jobj.put("password", password)
                    jobj.put("otp", otp)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                AndroidNetworking.post("https://risfund.loophole.site/api/login")
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

                                        homeData()
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
                                    Toast.makeText(this@LoginActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    })
            }

        }
    }
}