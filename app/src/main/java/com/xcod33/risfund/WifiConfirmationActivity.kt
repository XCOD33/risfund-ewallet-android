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
import kotlinx.android.synthetic.main.activity_wifi_confirmation.*
import org.json.JSONException
import org.json.JSONObject

class WifiConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_confirmation)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        val intent = intent
        val noCustomer = intent.getStringExtra("noCustomer")
        noMemberWifiTextView.text = noCustomer
        val name = intent.getStringExtra("name")
        nameMemberWifiTextView.text = name
        val provider = intent.getStringExtra("provider")
        providerNameWifiTextView.text = provider
        val packageName = intent.getStringExtra("packageName")
        packageNameWifiTextView.text = packageName
        val bill = intent.getStringExtra("bill")
        periodeWifiTextView.text = bill
        val amount = intent.getStringExtra("amount")
        amountWifiTextView.text = amount

        konfirmasiWifiButton.setOnClickListener {
            startTransaction(user)
        }
    }

    private fun startTransaction(user: GetUserResponse?) {
        val jobj = JSONObject()
        try {
            jobj.put("customer_no", intent.getStringExtra("noCustomer"))
            jobj.put("bill", intent.getStringExtra("bill"))
            jobj.put("amount", intent.getStringExtra("amount").toString().toInt())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        AndroidNetworking.post("https://risfund.loophole.site/api/purchase/wifi")
            .addJSONObjectBody(jobj)
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if(response!!.getString("message").equals("Purchase pdam succeeded")) {
                            val data = JSONObject(response.getString("data"))
                            val amount = data.getString("amount")
                            val purchaseType = data.getString("purchaseType")

                            val bundle = Bundle()
                            bundle.putString("amount", amount)
                            bundle.putString("purchaseType", purchaseType)
                            val intent = Intent(this@WifiConfirmationActivity, PurchasesSuccessActivity::class.java)
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
                        Toast.makeText(this@WifiConfirmationActivity, response.getString("message"), Toast.LENGTH_LONG).show()

                        Log.d("error", anError.errorBody.toString())
                    } else {
                        anError.toString()
                    }
                }
            })
    }
}