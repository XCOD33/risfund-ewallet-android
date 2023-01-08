package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_pulsa.*
import org.json.JSONException
import org.json.JSONObject

class PulsaActivity : AppCompatActivity() {

    private lateinit var backPulsa: ImageButton
    private lateinit var providerPulsaImageView: ImageView
    private lateinit var providerPulsaAutoComplete: AutoCompleteTextView

    private val userList = ArrayList<GetUserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulsa)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        backPulsa = findViewById(R.id.backPulsa)
        providerPulsaImageView = findViewById(R.id.providerPulsaImageView)
        providerPulsaAutoComplete = findViewById(R.id.providerPulsaAutoComplete)
        val providerList = listOf("Telkomsel", "Tri", "XL", "Axis", "Smartfren")
        val providerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, providerList)

        providerPulsaAutoComplete.setAdapter(providerAdapter)
        providerPulsaAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> providerPulsaImageView.setImageResource(R.drawable.telkomsel)
                1 -> providerPulsaImageView.setImageResource(R.drawable.tri)
                2 -> providerPulsaImageView.setImageResource(R.drawable.xl)
                3 -> providerPulsaImageView.setImageResource(R.drawable.axis)
                4 -> providerPulsaImageView.setImageResource(R.drawable.smartfren)
            }
        }

        backPulsa.setOnClickListener{
            var intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        pulsa1ImageButton.setOnClickListener {
            createTransaction(5000, user)
        }

        pulsa2ImageButton.setOnClickListener {
            createTransaction(10000, user)
        }

        pulsa3ImageButton.setOnClickListener {
            createTransaction(15000, user)
        }

        pulsa4ImageButton.setOnClickListener {
            createTransaction(20000, user)
        }

        pulsa5ImageButton.setOnClickListener {
            createTransaction(25000, user)
        }

        pulsa6ImageButton.setOnClickListener {
            createTransaction(50000, user)
        }

        pulsa7ImageButton.setOnClickListener {
            createTransaction(100000, user)
        }

        pulsa8ImageButton.setOnClickListener {
            createTransaction(200000, user)
        }

    }

    private fun createTransaction(amount: Int?, user: GetUserResponse?) {
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        if(nomorTeleponPulsaEditText.text.isNullOrEmpty()) {
                nomorTeleponPulsaEditText.error = "Harap Masukkan nomor telepon"
                nomorTeleponPulsaEditText.isFocusable = true
        } else {
            val jobj = JSONObject()
            try {
                jobj.put("phoneNumber", nomorTeleponPulsaEditText.text)
                jobj.put("provider", providerPulsaAutoComplete.text.toString())
                jobj.put("amount", amount)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            AndroidNetworking.post("https://risfund.loophole.site/api/purchase/pulsa")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if(response!!.getString("message").equals("Purchase pulsa succeeded")) {
                                val data = JSONObject(response.getString("data"))

                                val purchaseType = data.getString("purchaseType")
                                val amount = data.getString("amount")

                                val bundle = Bundle()
                                bundle.putString("type", "pulsa")
                                bundle.putString("purchaseType", purchaseType)
                                bundle.putString("amount", amount)

                                val intent = Intent(this@PulsaActivity, PurchasesSuccessActivity::class.java)
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

                            Toast.makeText(this@PulsaActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                        } else {
                            anError.toString()
                        }
                    }
                })
        }

    }


}