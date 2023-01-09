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
import kotlinx.android.synthetic.main.activity_mtix.*
import kotlinx.android.synthetic.main.activity_voucher_fisik.*
import org.json.JSONException
import org.json.JSONObject

class MtixActivity : AppCompatActivity() {

    private lateinit var backMtix: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mtix)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        backMtix = findViewById(R.id.backMtix)

        backMtix.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        mTixImageButton1.setOnClickListener{
            createTransaction(50000, user)
        }

        mTixImageButton2.setOnClickListener{
            createTransaction(100000, user)
        }

        mTixImageButton3.setOnClickListener{
            createTransaction(150000, user)
        }

        mTixImageButton4.setOnClickListener{
            createTransaction(200000, user)
        }

        mTixImageButton5.setOnClickListener{
            createTransaction(250000, user)
        }

        mTixImageButton6.setOnClickListener{
            createTransaction(300000, user)
        }

        mTixImageButton7.setOnClickListener{
            createTransaction(350000, user)
        }

        mTixImageButton8.setOnClickListener{
            createTransaction(400000, user)
        }
    }

    private fun createTransaction(amount: Int?, user: GetUserResponse?){
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        if (nomorTeleponMtixEditText.text.isNullOrEmpty()){
            nomorTeleponMtixEditText.error = "Harap Masukkan nomor telepon"
            nomorTeleponMtixEditText.isFocusable = true
        }else {
            val jobj = JSONObject()
            try {
                jobj.put("phoneNumber", nomorTeleponMtixEditText.text)
                jobj.put("amount", amount)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://risfund.loophole.site/api/purchase/mtix")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if(response!!.getString("message").equals("Purchase mtix succeeded")) {
                                val data = JSONObject(response.getString("data"))

                                val purchaseType = data.getString("purchaseType")
                                val amount = data.getString("amount")

                                val bundle = Bundle()
                                bundle.putString("type", "M-Tix")
                                bundle.putString("purchaseType", purchaseType)
                                bundle.putString("amount", amount)

                                val intent = Intent(this@MtixActivity, PurchasesSuccessActivity::class.java)
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

                            Toast.makeText(this@MtixActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                        } else {
                            anError.toString()
                        }
                    }
                })
        }

    }
}