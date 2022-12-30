package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class PaymentSuccessActivity : AppCompatActivity() {

    private lateinit var backPembayaranSuccess : ImageButton
    private lateinit var pembayaranBerhasilButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        backPembayaranSuccess = findViewById(R.id.backPembayaranSukses)
        pembayaranBerhasilButton = findViewById(R.id.pembayaranBerhasilButton)

        backPembayaranSuccess.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        pembayaranBerhasilButton.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}