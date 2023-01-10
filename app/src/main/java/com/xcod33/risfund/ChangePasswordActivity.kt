package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_wifi.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        backChangePassword.setOnClickListener {
            val intent = Intent(this, SettingFragment::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        confirmChangePasswordButton.setOnClickListener {
            changePassword(user)
        }
    }

    private fun changePassword(user: GetUserResponse?) {
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        if (oldPasswordEditText.text.isNullOrEmpty()) {
            oldPasswordEditText.error = "Harap Masukkan Password Saat Ini"
            oldPasswordEditText.isFocusable = true
        } else if (newPasswordEditText.text.isNullOrEmpty()) {
            newPasswordEditText.error = "Harap Masukkan Password Baru"
            newPasswordEditText.isFocusable = true
        } else if (confirmNewPasswordEditText.text.isNullOrEmpty()) {
            confirmNewPasswordEditText.error = "Harap Masukkan Konfirmasi Password Baru"
            confirmNewPasswordEditText.isFocusable = true
        } else {
            val jobj = JSONObject()

            try {
                jobj.put("oldPassword", oldPasswordEditText.text.toString())
                jobj.put("newPassword", newPasswordEditText.text.toString())
                jobj.put("newPassword_confirmation", confirmNewPasswordEditText.text.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://risfund.loophole.site/api/user/change-password")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if (response!!.getString("message").equals("User Password has been changed")) {
                                val data = JSONArray(response.getString("data"))

                                val intent = Intent(this@ChangePasswordActivity, HomeActivity2::class.java)
                                intent.putExtra("dataUser", user)
                                startActivity(intent)
                            }
                        } catch (e: JSONException) {
                            Log.e("eResponse", e.toString())
                        }
                    }

                    override fun onError(anError: ANError?) {
                        if (anError!!.errorCode != 0) {
                            val response = JSONObject(anError.errorBody)

                            Toast.makeText(
                                this@ChangePasswordActivity,
                                response.getString("message"),
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            anError.toString()
                        }
                    }
                })
        }
    }
}