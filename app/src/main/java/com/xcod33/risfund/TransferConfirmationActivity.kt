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
import kotlinx.android.synthetic.main.activity_transfer_confirmation.*
import org.json.JSONException
import org.json.JSONObject

class TransferConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_confirmation)
        val intent = intent
        val transferTo = intent.getStringExtra("transferTo")
        payToTextView.text = transferTo
        val tranferNameTo = intent.getStringExtra("fullName")
        payToNameTextView.text = tranferNameTo
        val note = intent.getStringExtra("note")
        noteTextView.text = note
        val amount  = intent.getIntExtra("amount", 0)
        amountTextView.text = amount.toString()

        konfirmasiTransferButton.setOnClickListener {
            startTransfer()
        }
    }

    private fun startTransfer() {

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        val jobj = JSONObject()
        try {
            jobj.put("phoneNumber", intent.getStringExtra("transferTo").toString().trim())
            jobj.put("amount", intent.getIntExtra("amount", 0).toString().toInt())
            if(intent.getStringExtra("note")!!.isNotEmpty()) {
                jobj.put("note",intent.getStringExtra("note")!!.trim())
            }
        } catch (e: JSONException) {
            Log.e("Ejobj", e.toString())
        }

        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        AndroidNetworking.post("https://risfund.loophole.site/api/transfer")
            .addJSONObjectBody(jobj)
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if(response!!.getString("message").equals("Transfer Succeeded")) {
                            val data = JSONObject(response.getString("data"))
                            val transferId = data.getString("transferId")
                            val amount = data.getString("amount")

                            val intent = Intent(this@TransferConfirmationActivity, TransferSuccessActivity::class.java)
                            intent.putExtra("transferId", transferId)
                            intent.putExtra("amount", amount)
                            intent.putExtra("dataUser", user)
                            startActivity(intent)
                        }
                    } catch (e: JSONException) {
                        Log.e("Eresponse", e.toString())
                    }
                }

                override fun onError(anError: ANError?) {
                    if(anError!!.errorCode != 0) {
                        val response = JSONObject(anError.errorBody)
                        Toast.makeText(this@TransferConfirmationActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}