package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_transfer.*
import kotlinx.android.synthetic.main.activity_transfer_confirmation.*
import org.json.JSONException
import org.json.JSONObject
import kotlin.system.exitProcess

class TransferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        backTransfer.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        transferTransferButton.setOnClickListener{
            checkValidation()
        }
    }

    private fun checkValidation() {
        if(noHandphoneTransferEditText.text.isNullOrEmpty()) {
            noHandphoneTransferInputLayout.error = "Harap masukkan nomor ponsel tujuan"
        } else if(nominalTransferEditText.text.isNullOrEmpty()) {
            nominalTransferInputLayout.error = "Harap masukkan nominal"
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

            AndroidNetworking.post("https://79c9-125-160-101-0.ap.ngrok.io/api/check-phone-number")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
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
                            startActivity(intent)
                        }
                    }

                    override fun onError(anError: ANError?) {
                        if(anError!!.errorCode != 0) {
                            val response = JSONObject(anError.errorBody)
                            if(response.getString("message") != "User Found") {
                                Toast.makeText(this@TransferActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
        }
    }


}