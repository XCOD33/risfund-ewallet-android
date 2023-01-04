package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xcod33.risfund.data.User
import org.json.JSONException
import org.json.JSONObject

class HomeActivity : AppCompatActivity() {

    private lateinit var topUpButton: ImageButton
    private lateinit var paymentButton: ImageButton
    private lateinit var transferButton: ImageButton
    private lateinit var pulsaButton: ImageButton
    private lateinit var paketDataButton: ImageButton
    private lateinit var voucherButton: ImageButton
    private lateinit var plnButton: ImageButton
    private lateinit var pdamButton: ImageButton
    private lateinit var wifiButton: ImageButton
    private lateinit var mtixButton: ImageButton
    private lateinit var bpjsButton: ImageButton
    private lateinit var bottomNav: com.google.android.material.bottomnavigation.BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getbalance()

        topUpButton = findViewById(R.id.topUpButton)
        paymentButton = findViewById(R.id.paymentButton)
        transferButton = findViewById(R.id.pulsaButton)
        pulsaButton = findViewById(R.id.pulsaButton)
        paketDataButton = findViewById(R.id.paketDataButton)
        voucherButton = findViewById(R.id.voucherButton)
        plnButton = findViewById(R.id.plnButton)
        pdamButton = findViewById(R.id.pdamButton)
        wifiButton = findViewById(R.id.wifiButton)
        mtixButton = findViewById(R.id.mtixButton)
        bpjsButton = findViewById(R.id.bpjsButton)
        bottomNav = findViewById(R.id.bottomNav)
        transferButton = findViewById(R.id.transferButton)
        val balanceNotifTextView = findViewById<TextView>(R.id.balanceNotifTextView)

        balanceNotifTextView.visibility = View.INVISIBLE

        topUpButton.setOnClickListener {
            var intent = Intent(this, TopUpActivity::class.java)
            startActivity(intent)
        }

        paymentButton.setOnClickListener {
            var intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        transferButton.setOnClickListener {
            var intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }

        pulsaButton.setOnClickListener {
            var intent = Intent(this, PulsaActivity::class.java)
            startActivity(intent)
        }

        paketDataButton.setOnClickListener {
            var intent = Intent(this, PaketDataActivity::class.java)
            startActivity(intent)
        }

        voucherButton.setOnClickListener {
            var intent = Intent(this, VoucherFisikActivity::class.java)
            startActivity(intent)
        }

        plnButton.setOnClickListener {
            var intent = Intent(this, PlnPascabayarActivity::class.java)
            startActivity(intent)
        }

        pdamButton.setOnClickListener {
            var intent = Intent(this, PdamActivity::class.java)
            startActivity(intent)
        }

        wifiButton.setOnClickListener {
            var intent = Intent(this, WifiActivity::class.java)
            startActivity(intent)
        }

        mtixButton.setOnClickListener {
            var intent = Intent(this, MtixActivity::class.java)
            startActivity(intent)
        }

        bpjsButton.setOnClickListener {
            var intent = Intent(this, BpjsActivity::class.java)
            startActivity(intent)
        }

        transferButton.setOnClickListener {
            var intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeNavigation -> {
                    true
                }

                R.id.qrNavigation-> {
                    val intent = Intent(this, MyQrActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.historyNavigation -> {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.settingNavigation -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    private fun getbalance() {
        val balanceNotifTextView = findViewById<TextView>(R.id.balanceNotifTextView)

        val sessionManager = SessionManager(this)
        val token = JSONObject(sessionManager.getToken())

        AndroidNetworking.get("https://79c9-125-160-101-0.ap.ngrok.io/api/user")
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if (response != null) {
                            if(response.getString("message").equals("User found")) {
                                val data = JSONObject(response.getString("data"))
                                val balance = data.getString("balance")

                                Log.d("balance", balance)
                                Log.d("token", token.getString("token"))

                                val balanceTextView = findViewById<TextView>(R.id.balanceTextView)
                                balanceTextView.text = balance

                                if(balanceTextView.text.toString().toInt() < 100000) {
                                    balanceNotifTextView.visibility = View.VISIBLE
                                } else {
                                    balanceNotifTextView.visibility = View.GONE
                                }
                            }
                        }
                    } catch (error: JSONException) {
                        Log.d("error response", error.toString())
                    }
                }

                override fun onError(error: ANError?) {
                    if (error != null) {
                        if (error.errorCode != 0) {
                            val response = JSONObject(error.errorBody)
//                                Log.e("responseError", response.getString("message"))
                            Toast.makeText(this@HomeActivity, response.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
    }
}
