package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView

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
}