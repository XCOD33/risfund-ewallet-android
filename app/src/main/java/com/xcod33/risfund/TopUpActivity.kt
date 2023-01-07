package com.xcod33.risfund

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.xcod33.risfund.data.GetPaymentChannel
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_list_bank.*
import kotlinx.android.synthetic.main.activity_top_up.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class TopUpActivity : AppCompatActivity() {

    private lateinit var back: ImageButton
    private lateinit var TopupButton: Button
    private lateinit var nominalEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        usernameTopUpTextView.text = "Hi, ${user!!.fullName}"
        balanceTopUpTextView.text = "Rp${user!!.balance.toString()}"

        back = findViewById(R.id.back)
        TopupButton = findViewById(R.id.TopupButton)
        nominalEditText = findViewById(R.id.nominalEditText)

//        val imageStart = Glide.with(this).load()

//        paymentChannel.setCompoundDrawablesWithIntrinsicBounds()

        back.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        TopupButton.setOnClickListener{
            if(nominalEditText.text.isEmpty()) {
                nominalEditText.error = "Masukkan Nominal Top Up"
            } else {
                val intent = Intent(this, ListBankActivity::class.java)
                intent.putExtra("amount", nominalEditText.text.toString())
                startActivity(intent)
//                topup()
            }
        }
    }

//    private fun topup() {
//        val sessionManager = SessionManager(this)
//        val token = JSONObject(sessionManager.getToken())
//
//        val jobj = JSONObject()
//        try {
//            jobj.put("amount", nominalEditText.text)
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//
//        AndroidNetworking.post("https://risfund.loophole.site/api/topup")
//            .addJSONObjectBody(jobj)
//            .addHeaders("Accept", "application/json")
//            .addHeaders("Authorization", "Bearer " + token.getString("token"))
//            .setPriority(Priority.MEDIUM)
//            .build()
//            .getAsJSONObject(object: JSONObjectRequestListener {
//                override fun onResponse(response: JSONObject?) {
//                    try {
//                        if (response != null) {
//                            if(response.getString("message").equals("Topup Succeeded")) {
//                                val data = JSONArray(response.getString("data"))
//                                val tripay = data.getJSONObject(1)
//                                val dataTripay = tripay.getJSONObject("data")
//
//                                Log.e("data", dataTripay.getString("checkout_url"))
//
//                                val intent = Intent(this@TopUpActivity, TripayWebViewActivity::class.java)
//                                intent.putExtra("checkout_url", dataTripay.getString("checkout_url"))
//                                startActivity(intent)
//                            }
//                        }
//                    } catch (e: JSONException) {
//                        Log.d("error", e.toString())
//                    }
//                }
//
//                override fun onError(error: ANError?) {
//                    if (error != null) {
//                        if (error.errorCode != 0) {
//                            val response = JSONObject(error.errorBody)
//                            Log.e("responseError", response.getString("message"))
//                            Toast.makeText(this@TopUpActivity, response.getString("message"), Toast.LENGTH_LONG).show()
//                        }
//                    }
//                }
//            })
//    }
}
