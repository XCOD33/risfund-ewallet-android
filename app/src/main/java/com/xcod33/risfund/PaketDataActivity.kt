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
import kotlinx.android.synthetic.main.activity_pulsa.*
import org.json.JSONException
import org.json.JSONObject

class PaketDataActivity : AppCompatActivity() {

    private lateinit var backPaketData: ImageButton
    private lateinit var providerPaketDataAutoComplete: AutoCompleteTextView
    private lateinit var providerPaketDataImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket_data)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        backPaketData = findViewById(R.id.backPaketData)
        providerPaketDataAutoComplete = findViewById(R.id.providerPaketDataAutoComplete)
        providerPaketDataImageView = findViewById(R.id.providerPaketDataImageView)

        val providerList = listOf("Telkomsel", "Tri", "XL", "Axis", "Smartfren")
        val providerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, providerList)

        providerPaketDataAutoComplete.setAdapter(providerAdapter)
        providerPaketDataAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> providerPaketDataImageView.setImageResource(R.drawable.telkomsel)
                1 -> providerPaketDataImageView.setImageResource(R.drawable.tri)
                2 -> providerPaketDataImageView.setImageResource(R.drawable.xl)
                3 -> providerPaketDataImageView.setImageResource(R.drawable.axis)
                4 -> providerPaketDataImageView.setImageResource(R.drawable.smartfren)
            }
        }

        backPaketData.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        paketData1ImageButton.setOnClickListener{
            createTransaction(40000, user)
        }

        paketData2ImageButton.setOnClickListener{
            createTransaction(50000, user)
        }

        paketData3ImageButton.setOnClickListener{
            createTransaction(60000, user)
        }

        paketData4ImageButton.setOnClickListener{
            createTransaction(75000, user)
        }

        paketData5ImageButton.setOnClickListener{
            createTransaction(85000, user)
        }

        paketData6ImageButton.setOnClickListener{
            createTransaction(100000, user)
        }

        paketData7ImageButton.setOnClickListener{
            createTransaction(110000, user)
        }

        paketData8ImageButton.setOnClickListener{
            createTransaction(125000, user)
        }
    }

    private fun createTransaction(amount: Int?, user: GetUserResponse?){
        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        if (nomorTeleponPaketDataEditText.text.isNullOrEmpty()){
            nomorTeleponPaketDataEditText.error = "Harap Masukkan nomor telepon"
            nomorTeleponPaketDataEditText.isFocusable = true
        } else {
            val jobj = JSONObject()
            try {
                jobj.put("phoneNumber", nomorTeleponPaketDataEditText.text)
                jobj.put("provider", providerPaketDataAutoComplete.text.toString())
                jobj.put("amount", amount)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            AndroidNetworking.post("https://risfund.loophole.site/api/purchase/paket-data")
                .addJSONObjectBody(jobj)
                .addHeaders("Accept", "application/json")
                .addHeaders("Authorization", "Bearer " + token.getString("token"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object: JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            if(response!!.getString("message").equals("Purchase paket data succeeded")) {
                                val data = JSONObject(response.getString("data"))

                                val purchaseType = data.getString("purchaseType")
                                val amount = data.getString("amount")

                                val bundle = Bundle()
                                bundle.putString("type", "paket data")
                                bundle.putString("purchaseType", purchaseType)
                                bundle.putString("amount", amount)

                                val intent = Intent(this@PaketDataActivity, PurchasesSuccessActivity::class.java)
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

                            Toast.makeText(this@PaketDataActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                        } else {
                            anError.toString()
                        }
                    }
                })

        }
    }
}