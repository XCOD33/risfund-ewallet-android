package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var usernameInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var loginButton : Button
    private lateinit var daftar2TextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        initiate FAN
        AndroidNetworking.initialize(applicationContext)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        usernameInputLayout = findViewById(R.id.usernameInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        loginButton = findViewById(R.id.loginButton)
        daftar2TextView = findViewById(R.id.daftar2TextView)

        loginButton.setOnClickListener {
            login()
        }

        daftar2TextView.setOnClickListener {
            val intent = Intent(this, Register1Activity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val phoneNumber = usernameEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (usernameEditText.text.isEmpty()) {
            usernameInputLayout.error = "Username is required"
        } else if (passwordEditText.text.isEmpty()) {
            passwordInputLayout.error = "Password is required"
        } else {
            val jobj = JSONObject()
            try {
                jobj.put("phoneNumber", phoneNumber)
                jobj.put("password", password)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://8718-125-160-101-0.ap.ngrok.io/api/login")
                .addJSONObjectBody(jobj)
                .addHeaders("Content-Type", "application/json")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        Log.d("response", response.toString())
                        try {
                            if (response != null) {
                                if(response.getString("message").equals("Login Succeeded")) {
                                    Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_LONG).show()

                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                }
                                Log.d("response", response.toString())
                            }
                        } catch (e: JSONException) {
                            Log.d("error", e.toString())
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Log.d("error", anError.toString())
                    }
                })
        }
    }
}