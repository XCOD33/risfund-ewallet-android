package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_bpjs.*
import kotlinx.android.synthetic.main.activity_pdam.*
import org.json.JSONException
import org.json.JSONObject

class BpjsActivity : AppCompatActivity() {

    private lateinit var backBpjs: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bpjs)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        backBpjs = findViewById(R.id.backBpjs)

        backBpjs.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }
        BpjsButton.setOnClickListener {
            createTransaction(user)
        }
    }

    private fun createTransaction(user: GetUserResponse?) {
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())
        val noMember = nomorKartuBpjsEditText.text.toString()

        if (nomorKartuBpjsEditText.text.isNullOrEmpty()) {
            nomorKartuBpjsEditText.error = "Harap Masukkan Nomor Kartu"
            nomorKartuBpjsEditText.isFocusable = true
        } else {
            val jobj = JSONObject()

            try {
                jobj.put("customer_no", noMember)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://risfund.loophole.site/api/purchase/inquiry/bpjs")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if (response!!.getString("message")
                                    .equals("data customer ${noMember} found")
                            ) {
                                val data = JSONObject(response.getString("data"))

                                val name = data.getString("name")
                                val bill = data.getString("bill")
                                val amount = data.getString("amount")

                                val bundle = Bundle()
                                bundle.putString("noMember", noMember)
                                bundle.putString("name", name)
                                bundle.putString("bill", bill)
                                bundle.putString("amount", amount)

                                val intent =
                                    Intent(this@BpjsActivity, BpjsConfirmationActivity::class.java)
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
                                this@BpjsActivity,
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