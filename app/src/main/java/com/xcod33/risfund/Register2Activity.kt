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

class Register2Activity : AppCompatActivity() {

    private lateinit var usernameRegisterInputLayout: TextInputLayout
    private lateinit var usernameRegisterEditText: EditText
    private lateinit var passwordRegisterInputLayout: TextInputLayout
    private lateinit var passwordRegisterEditText: EditText
    private lateinit var rePasswordRegisterInputLayout: TextInputLayout
    private lateinit var rePasswordRegisterEditText: EditText
    private lateinit var daftarRegisterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        val bundle = intent.extras
        val name = bundle?.getString("name")
        val phoneNumber = bundle?.getString("phoneNumber")
        val gender = bundle?.getString("gender")
        val birthdate = bundle?.getString("birthdate")

        usernameRegisterInputLayout = findViewById(R.id.usernameRegisterInputLayout)
        usernameRegisterEditText = findViewById(R.id.usernameRegisterEditText)
        passwordRegisterInputLayout = findViewById(R.id.passwordRegisterInputLayout)
        passwordRegisterEditText = findViewById(R.id.passwordRegisterEditText)
        rePasswordRegisterInputLayout = findViewById(R.id.rePasswordRegisterInputLayout)
        rePasswordRegisterEditText = findViewById(R.id.rePasswordRegisterEditText)
        daftarRegisterButton = findViewById(R.id.daftarRegisterButton)

        daftarRegisterButton.setOnClickListener {
            if (usernameRegisterEditText.text.isEmpty()) {
                usernameRegisterInputLayout.error = "Username diperlukan"
                usernameRegisterInputLayout.requestFocus()
            } else if (passwordRegisterEditText.text.isEmpty()) {
                passwordRegisterInputLayout.error = "Password diperlukan"
            } else if (rePasswordRegisterEditText.text.isEmpty()) {
                rePasswordRegisterInputLayout.error = "Re-Password diperlukan"
            } else {
                val username = usernameRegisterEditText.text.toString().trim()
                val password = passwordRegisterEditText.text.toString().trim()
                val rePassword = rePasswordRegisterEditText.text.toString().trim()

                val jobj = JSONObject()
                try {
                    jobj.put("fullName", name)
                    jobj.put("phoneNumber", phoneNumber)
                    jobj.put("gender", gender)
                    jobj.put("birthdate", birthdate)
                    jobj.put("username", username)
                    jobj.put("password", password)
                    jobj.put("password_confirmation", rePassword)
                } catch (e: JSONException) {
                    Log.d("error", e.toString())
                }

                AndroidNetworking.post("https://8718-125-160-101-0.ap.ngrok.io/api/register")
                    .addJSONObjectBody(jobj)
                    .addHeaders("Content-Type", "application/json")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object: JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject?) {
                            try {
                                if (response != null) {
                                    if (response.getString("message").equals("Registration succeeded")) {
                                        Toast.makeText(this@Register2Activity, response.getString("message"), Toast.LENGTH_LONG).show()

                                        val intent = Intent(this@Register2Activity, LoginActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                            } catch (e: JSONException) {
                                Log.e("error", e.toString())
                            }
                        }

                        override fun onError(anError: ANError?) {
                            Log.e("error", anError.toString())
                        }
                    })
            }
        }
    }
}