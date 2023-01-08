package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_transfer.*
import org.json.JSONException
import org.json.JSONObject

class TransferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        usernameTransferTextView.text = "Hi, ${user!!.fullName}"
        balanceTraansferTextView.text = "Rp${user!!.balance.toString()}"

        balanceNotifTransferTextView.isVisible = user.balance!! < 100000

        backTransfer.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        transferTransferButton.setOnClickListener{
            if(noHandphoneTransferEditText.text.isNullOrEmpty()) {
            noHandphoneTransferInputLayout.error = "Harap masukkan nomor ponsel tujuan"
        } else if(nominalTransferEditText.text.isNullOrEmpty()) {
            nominalTransferInputLayout.error = "Harap masukkan nominal"
        } else if(nominalTransferEditText.text.toString().toInt() > user.balance!!) {
            nominalTransferInputLayout.error = "Maaf saldo anda tidak mencukupi"
        } else {
            val bundle = Bundle()
            bundle.putString("transferTo", noHandphoneTransferEditText.text.toString())
            bundle.putString("note", catatanTransferEditText.text.toString())
            bundle.putInt("amount", nominalTransferEditText.text.toString().toInt())

            val jobj = JSONObject()
            try {
                jobj.put("phoneNumber", noHandphoneTransferEditText.text.toString().trim())
            } catch (e: JSONException) {
                Log.e("Ejobj", e.toString())
            }

            val sessionManager = SessionManager(this)
            val token = JSONObject(sessionManager.getToken())
            AndroidNetworking.post("https://risfund.loophole.site/api/check-phone-number")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        if(response!!.getString("message").equals("User Found")) {
                            val data = JSONObject(response.getString("data"))
                            val fullName = data.getString("fullName")
                            bundle.putString("fullName", fullName)

                            Log.d("data", data.toString())

                            val intent = Intent(this@TransferActivity, TransferConfirmationActivity::class.java)
                            intent.putExtras(bundle)
                            intent.putExtra("dataUser", user)
                            startActivity(intent)
                        }
                    }

                    override fun onError(anError: ANError?) {
                        if(anError!!.errorCode != 0) {
                            val response = JSONObject(anError.errorBody)
                            when(response.getString("message")) {
                                "User not found" -> noHandphoneTransferInputLayout.error = response.getString("message")
                                "Error check" -> noHandphoneTransferInputLayout.error = JSONObject(response.getString("data")).getString("error")
                            }
                        }
                    }
                })
        }
        }
    }


}