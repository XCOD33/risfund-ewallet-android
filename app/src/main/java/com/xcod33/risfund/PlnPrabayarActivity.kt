package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_pln_pascabayar.*
import kotlinx.android.synthetic.main.activity_pln_prabayar.*
import org.json.JSONException
import org.json.JSONObject

class PlnPrabayarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pln_prabayar)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        val plnList = listOf("PLN Pascabayar", "PLN Prabayar")
        val plnAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, plnList)

        plnPrabayarAutoComplete.setAdapter(plnAdapter)
        plnPrabayarAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> {
                    val intent = Intent(this, PlnPascabayarActivity::class.java)
                    intent.putExtra("dataUser", user)
                    startActivity(intent)
                }
            }
        }

        backPlnPrabayar.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        plnPrabayarImageButton1.setOnClickListener {
            createTransaction(20000, user)
        }

        plnPrabayarImageButton2.setOnClickListener {
            createTransaction(50000, user)
        }

        plnPrabayarImageButton3.setOnClickListener {
            createTransaction(100000, user)
        }

        plnPrabayarImageButton4.setOnClickListener {
            createTransaction(400000, user)
        }

        plnPrabayarImageButton5.setOnClickListener {
            createTransaction(500000, user)
        }

        plnPrabayarImageButton6.setOnClickListener {
            createTransaction(1000000, user)
        }

        plnPrabayarImageButton7.setOnClickListener {
            createTransaction(5000000, user)
        }

        plnPrabayarImageButton8.setOnClickListener {
            createTransaction(10000000, user)
        }
    }

    private fun createTransaction(amount: Int?, user: GetUserResponse?) {
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        if (nomorMeteranPlnPrabayarEditText.text.isNullOrEmpty()) {
            nomorMeteranPlnPrabayarEditText.error = "Harap Masukkan Nomor Meteran"
            nomorMeteranPlnPrabayarEditText.isFocusable = true
        } else {
            val jobj = JSONObject()

            try {
                jobj.put("meter_no", nomorMeteranPlnPrabayarEditText.text.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://risfund.loophole.site/api/purchase/inquiry/pln-prepaid")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if (response!!.getString("message").equals("data customer pln found")) {
                                val data = JSONObject(response.getString("data"))

                                val name = data.getString("name")
                                val segmentPower = data.getString("segment_power")

                                val bundle = Bundle()
                                bundle.putString("noMeter", nomorMeteranPlnPrabayarEditText.text.toString())
                                bundle.putString("name", name)
                                bundle.putString("segmentPower", segmentPower)
                                bundle.putString("amount", amount.toString())

                                val intent = Intent(this@PlnPrabayarActivity, PlnPrabayarConfirmationActivity::class.java)
                                intent.putExtras(bundle)
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
                                this@PlnPrabayarActivity,
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