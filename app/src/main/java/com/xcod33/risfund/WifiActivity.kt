package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_pdam.*
import kotlinx.android.synthetic.main.activity_wifi.*
import org.json.JSONException
import org.json.JSONObject

class WifiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        val wifiList = listOf("Indihome", "Biznet", "MNC Play", "First Media", "MyRepublic")
        val wifiAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, wifiList)

        wifiAutoComplete.setAdapter(wifiAdapter)
        wifiAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> wifiImageView.setImageResource(R.drawable.logoindihome)
                1 -> wifiImageView.setImageResource(R.drawable.biznet)
                2 -> wifiImageView.setImageResource(R.drawable.mncplay)
                3 -> wifiImageView.setImageResource(R.drawable.firstmedia)
                4 -> wifiImageView.setImageResource(R.drawable.myrepublic)
            }
        }

        backWifi.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        wifiButton.setOnClickListener {
            createTransaction(user)
        }

    }

    private fun createTransaction(user: GetUserResponse?) {
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())
        val provider = wifiAutoComplete.text.toString()

        if (nomorPelangganWifiEditText.text.isNullOrEmpty()) {
            nomorPelangganWifiEditText.error = "Harap Masukkan Nomor Pelanggan"
            nomorPelangganWifiEditText.isFocusable = true
        } else {
            val jobj = JSONObject()

            try {
                jobj.put("customer_no", nomorPelangganWifiEditText.text.toString())
                jobj.put("provider", provider)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://risfund.loophole.site/api/purchase/inquiry/wifi")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if (response!!.getString("message").equals("data customer ${provider} found")) {
                                val data = JSONObject(response.getString("data"))

                                val noCustomer = data.getString("customer_no")
                                val name = data.getString("name")
                                val packageName = data.getString("packageName")
                                val bill = data.getString("bill")
                                val amount = data.getString("amount")

                                val bundle = Bundle()
                                bundle.putString("noCustomer", noCustomer)
                                bundle.putString("name", name)
                                bundle.putString("provider", provider)
                                bundle.putString("packageName", packageName)
                                bundle.putString("bill", bill)
                                bundle.putString("amount", amount)

                                val intent =
                                    Intent(this@WifiActivity, WifiConfirmationActivity::class.java)
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
                                this@WifiActivity,
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