package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_paket_data.*
import kotlinx.android.synthetic.main.activity_voucher_fisik.*
import org.json.JSONException
import org.json.JSONObject

class VoucherFisikActivity : AppCompatActivity() {

    private lateinit var backVoucherFisik: ImageButton
    private lateinit var voucherImageView: ImageView
    private lateinit var jenisVoucherAutoComplete: AutoCompleteTextView
    private lateinit var voucherRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher_fisik)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        backVoucherFisik = findViewById(R.id.backVoucherFisik)
        voucherImageView = findViewById(R.id.voucherImageView)
        jenisVoucherAutoComplete = findViewById(R.id.jenisVoucherAutoComplete)

        val voucherList = listOf("Google Play Store", "Spotify")
        val voucherAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, voucherList)

        backVoucherFisik.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        jenisVoucherAutoComplete.setAdapter(voucherAdapter)
        jenisVoucherAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> voucherImageView.setImageResource(R.drawable.playstore)
                1 -> voucherImageView.setImageResource(R.drawable.spotify)
            }
        }

        voucherFisik1ImageButton.setOnClickListener{
            createTransaction(20000, user)
        }

        voucherFisik2ImageButton.setOnClickListener{
            createTransaction(50000, user)
        }

        voucherFisik3ImageButton.setOnClickListener{
            createTransaction(100000, user)
        }

        voucherFisik4ImageButton.setOnClickListener{
            createTransaction(150000, user)
        }

        voucherFisik5ImageButton.setOnClickListener{
            createTransaction(250000, user)
        }

        voucherFisik6ImageButton.setOnClickListener{
            createTransaction(300000, user)
        }

        voucherFisik7ImageButton.setOnClickListener{
            createTransaction(500000, user)
        }

        voucherFisik8ImageButton.setOnClickListener{
            createTransaction(750000, user)
        }
    }

    private fun createTransaction(amount: Int?, user: GetUserResponse?){
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        if (emailVoucherFisikEditText.text.isNullOrEmpty()){
            emailVoucherFisikEditText.error = "Harap Masukkan Email"
            emailVoucherFisikEditText.isFocusable = true
        }else {
            val jobj = JSONObject()
            try {
                jobj.put("email", emailVoucherFisikEditText.text)
                jobj.put("productName", jenisVoucherAutoComplete.text.toString())
                jobj.put("amount", amount)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            AndroidNetworking.post("https://risfund.loophole.site/api/purchase/voucher")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if(response!!.getString("message").equals("Purchase voucher succeeded")) {
                                val data = JSONObject(response.getString("data"))

                                val purchaseType = data.getString("purchaseType")
                                val amount = data.getString("amount")

                                val bundle = Bundle()
                                bundle.putString("type", "voucher")
                                bundle.putString("purchaseType", purchaseType)
                                bundle.putString("amount", amount)

                                val intent = Intent(this@VoucherFisikActivity, PurchasesSuccessActivity::class.java)
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

                            Toast.makeText(this@VoucherFisikActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                        } else {
                            anError.toString()
                        }
                    }
                })

        }
    }
}