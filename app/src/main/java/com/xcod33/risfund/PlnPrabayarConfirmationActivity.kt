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
import kotlinx.android.synthetic.main.activity_pln_pascabayar_confirmation.*
import kotlinx.android.synthetic.main.activity_pln_prabayar_confirmation.*
import org.json.JSONException
import org.json.JSONObject

class PlnPrabayarConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pln_prabayar_confirmation)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        val intent = intent
        val noMeter = intent.getStringExtra("noMeter")
        noMeterPrabayarTextView.text = noMeter
        val name = intent.getStringExtra("name")
        nameMemberPrabayarTextView.text = name
        val segmentPower = intent.getStringExtra("segmentPower")
        dayaPrabayarTextView.text = segmentPower
        val amount = intent.getStringExtra("amount")
        amountPrabayarTextView.text = amount

        backKonfirmasiPrabayarPembayaran.setOnClickListener {
            var intent = Intent ( this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        konfirmasiPembayaranPrabayarButton.setOnClickListener {
            startPurchase(user)
        }
    }

    private fun startPurchase(user: GetUserResponse?) {
        val jobj = JSONObject()
        try {
            jobj.put("meter_no", intent.getStringExtra("noMeter"))
            jobj.put("amount", intent.getStringExtra("amount").toString().toInt())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        AndroidNetworking.post("https://risfund.loophole.site/api/purchase/pln-prepaid")
            .addJSONObjectBody(jobj)
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if(response!!.getString("message").equals("Purchase pln prepaid succeeded")) {
                            val data = JSONObject(response.getString("data"))
                            val amount = data.getString("amount")
                            val purchaseType = data.getString("purchaseType")

                            val bundle = Bundle()
                            bundle.putString("amount", amount)
                            bundle.putString("purchaseType", purchaseType)
                            val intent = Intent(this@PlnPrabayarConfirmationActivity, PurchasesSuccessActivity::class.java)
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

                        Toast.makeText(this@PlnPrabayarConfirmationActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                    } else {
                        anError.toString()
                    }
                }
            })
    }
}