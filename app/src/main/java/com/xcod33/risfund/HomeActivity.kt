package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
import org.json.JSONException
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val intent = intent
        val userId = intent.getStringExtra("userId")
        val fullName = intent.getStringExtra("fullName")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val birthdate = intent.getStringExtra("birthdate")
        val gender = intent.getStringExtra("gender")
        val username = intent.getStringExtra("username")
        val balance = intent.getStringExtra("balance")
        val userQr = intent.getStringExtra("userQr")

        usernameTextView.text = "Hi, $fullName"

        balanceTextView.text = balance.toString()
            if(balanceTextView.text.toString().toInt() < 100000) {
                balanceNotifTextView.visibility = View.VISIBLE
            } else {
                balanceNotifTextView.visibility = View.GONE
            }


        topUpButton.setOnClickListener {
            val intent = Intent(this, TopUpActivity::class.java)
            startActivity(intent)
        }

        paymentButton.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }

        transferButton.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }

        pulsaButton.setOnClickListener {
            val intent = Intent(this, PulsaActivity::class.java)
            startActivity(intent)
        }

        paketDataButton.setOnClickListener {
            val intent = Intent(this, PaketDataActivity::class.java)
            startActivity(intent)
        }

        voucherButton.setOnClickListener {
            val intent = Intent(this, VoucherFisikActivity::class.java)
            startActivity(intent)
        }

        plnButton.setOnClickListener {
            val intent = Intent(this, PlnPascabayarActivity::class.java)
            startActivity(intent)
        }

        pdamButton.setOnClickListener {
            val intent = Intent(this, PdamActivity::class.java)
            startActivity(intent)
        }

        wifiButton.setOnClickListener {
            val intent = Intent(this, WifiActivity::class.java)
            startActivity(intent)
        }

        mtixButton.setOnClickListener {
            val intent = Intent(this, MtixActivity::class.java)
            startActivity(intent)
        }

        bpjsButton.setOnClickListener {
            var intent = Intent(this, BpjsActivity::class.java)
            startActivity(intent)
        }

        transferButton.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
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
}
