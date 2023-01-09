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
import org.json.JSONException
import org.json.JSONObject

class PlnPascabayarActivity : AppCompatActivity() {

    private lateinit var backPlnPascabayar: ImageButton
    private lateinit var plnPascabayarAutoComplete: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pln_pascabayar)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        backPlnPascabayar = findViewById(R.id.backPlnPascabayar)
        plnPascabayarAutoComplete = findViewById(R.id.plnPascabayarAutoComplete)

        val plnList = listOf("PLN Pascabayar", "PLN Prabayar")
        val plnAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, plnList)

        plnPascabayarAutoComplete.setAdapter(plnAdapter)
        plnPascabayarAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                1 -> {
                    val intent = Intent(this, PlnPrabayarActivity::class.java)
                    intent.putExtra("dataUser", user)
                    startActivity(intent)
                }
            }
        }

        backPlnPascabayar.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        plnPascabayarButton.setOnClickListener {
            createTransaction(user)
        }
    }

    private fun createTransaction(user: GetUserResponse?) {
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        if (nomorMeteranPlnPascabayarEditText.text.isNullOrEmpty()) {
            nomorMeteranPlnPascabayarEditText.error = "Harap Masukkan Nomor Meteran"
            nomorMeteranPlnPascabayarEditText.isFocusable = true
        } else {
            val jobj = JSONObject()

            try {
                jobj.put("meter_no", nomorMeteranPlnPascabayarEditText.text.toString())
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://risfund.loophole.site/api/purchase/inquiry/pln-postpaid")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if(response!!.getString("message").equals("data customer pln found")) {
                                val data = JSONObject(response.getString("data"))

                                val name = data.getString("name")
                                val segmentPower = data.getString("segment_power")
                                val bill = data.getString("bill")
                                val amount = data.getString("amount")

                                val bundle = Bundle()
                                bundle.putString("noMeter", nomorMeteranPlnPascabayarEditText.text.toString())
                                bundle.putString("name", name)
                                bundle.putString("segmentPower", segmentPower)
                                bundle.putString("bill", bill)
                                bundle.putString("amount", amount)

                                val intent = Intent(this@PlnPascabayarActivity, PurchasesConfirmationActivity::class.java)
                                intent.putExtras(bundle)
                                intent.putExtra("dataUser", user)
                                startActivity(intent)
                            }
                        } catch (e: JSONException) {
                            Log.e("eResponse", e.toString())
                        }
                    }

                    override fun onError(anError: ANError?) {
                        if(anError!!.errorCode != 0) {
                            val response = JSONObject(anError.errorBody)

                            Toast.makeText(this@PlnPascabayarActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                        } else {
                            anError.toString()
                        }
                    }
                })
        }
    }
}