package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView

class VoucherFisikActivity : AppCompatActivity() {

    private lateinit var backVoucherFisik: ImageButton
    private lateinit var voucherImageView: ImageView
    private lateinit var jenisVoucherAutoComplete: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher_fisik)

        backVoucherFisik = findViewById(R.id.backVoucherFisik)
        voucherImageView = findViewById(R.id.voucherImageView)
        jenisVoucherAutoComplete = findViewById(R.id.jenisVoucherAutoComplete)
        val voucherList = listOf("Google Play Store", "Spotify")
        val voucherAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, voucherList)

        jenisVoucherAutoComplete.setAdapter(voucherAdapter)
        jenisVoucherAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> voucherImageView.setImageResource(R.drawable.playstore)
                1 -> voucherImageView.setImageResource(R.drawable.spotify)
            }
        }

        backVoucherFisik.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}