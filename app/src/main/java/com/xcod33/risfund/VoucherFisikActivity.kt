package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CursorAdapter
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VoucherFisikActivity : AppCompatActivity() {

    private lateinit var backVoucherFisik: ImageButton
    private lateinit var voucherImageView: ImageView
    private lateinit var jenisVoucherAutoComplete: AutoCompleteTextView
    private lateinit var voucherRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher_fisik)

        backVoucherFisik = findViewById(R.id.backVoucherFisik)
        voucherImageView = findViewById(R.id.voucherImageView)
        jenisVoucherAutoComplete = findViewById(R.id.jenisVoucherAutoComplete)
        voucherRecyclerView = findViewById(R.id.voucherRecyclerView)

        val voucherList = listOf("Google Play Store", "Spotify")
        val voucherAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, voucherList)

        backVoucherFisik.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        jenisVoucherAutoComplete.setAdapter(voucherAdapter)
        jenisVoucherAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> voucherImageView.setImageResource(R.drawable.playstore)
                1 -> voucherImageView.setImageResource(R.drawable.spotify)
            }
        }

        voucherRecyclerView.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ItemsViewModelVoucher>()
        for(i in 1..7) {
            data.add(ItemsViewModelVoucher(i, i))
        }

        val adapter = CustomAdapterVoucher(data)
        voucherRecyclerView.adapter = adapter

    }
}