package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class VoucherFisikActivity : AppCompatActivity() {

    private lateinit var backVoucherFisik: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher_fisik)

        backVoucherFisik = findViewById(R.id.backVoucherFisik)

        backVoucherFisik.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}