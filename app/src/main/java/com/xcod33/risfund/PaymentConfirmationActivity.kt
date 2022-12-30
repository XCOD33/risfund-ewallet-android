package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class PaymentConfirmationActivity : AppCompatActivity() {
    private lateinit var backKonfirmasiPembayaran: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirmation)

        backKonfirmasiPembayaran = findViewById(R.id.backKonfirmasiPembayaran)

        backKonfirmasiPembayaran.setOnClickListener{
            var intent = Intent ( this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}