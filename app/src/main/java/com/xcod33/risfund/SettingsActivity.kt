package com.xcod33.risfund

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_settings.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        bottomNav.setSelectedItemId(R.id.settingNavigation);

        tanggalLahirProfilEditText.setOnClickListener {
            tanggalLahirProfilInputLayout.isHintEnabled = false
            val dpd = DatePickerDialog(this, android.R.style.Theme_Material_Dialog,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mMonth1 = mMonth + 1
                tanggalLahirProfilEditText.setText("" + mYear + "/" + mMonth1 + "/" + mDay)
            }, year, month, day)
            dpd.show()
        }

        editProfilButton.setOnClickListener {
            namaLengkapProfilEditText.setText(namaLengkapProfilEditText.text.toString())
            nomorTeleponProfilEditText.setText(nomorTeleponProfilEditText.text.toString())
        }

        logoutButton.setOnClickListener {
            logout()
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeNavigation -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.qrNavigation-> {
                    val intent = Intent(this, MyQrActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.historyNavigation -> {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.settingNavigation -> {
                    true
                }

                else -> false
            }
        }
    }

    private fun logout() {
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())
        AndroidNetworking.post("https://risfund.loophole.site/api/logout")
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if (response != null) {
                            if(response.getString("message").equals("success")) {
                                val logout = sessionManager.clearToken()

                                val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
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
                            Toast.makeText(this@SettingsActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
    }
}