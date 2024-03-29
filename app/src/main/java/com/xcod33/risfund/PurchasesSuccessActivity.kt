package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_purchases_success.*

class PurchasesSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchases_success)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        backPembayaranSukses.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        pembayaranBerhasilButton.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        val intent = intent
        titleSuccessTextView.text = intent.getStringExtra("purchaseType")
        PembayaranAmountTextView.text = "- Rp " + intent.getStringExtra("amount")
//        if(intent.getStringExtra("type") == "pulsa") {
//            titleSuccessTextView.text = intent.getStringExtra("purchaseType")
//            PembayaranAmountTextView.text = "- Rp " + intent.getStringExtra("amount")
//        } else if (intent.getStringExtra("type") == "paket data") {
//
//        }

    }
}