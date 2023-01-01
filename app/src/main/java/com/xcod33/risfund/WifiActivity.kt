package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView

class WifiActivity : AppCompatActivity() {

    private lateinit var backWifi: ImageButton
    private lateinit var wifiImageView: ImageView
    private lateinit var wifiAutoComplete: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi)

        backWifi = findViewById(R.id.backWifi)
        wifiImageView = findViewById(R.id.wifiImageView)
        wifiAutoComplete = findViewById(R.id.wifiAutoComplete)
        val wifiList = listOf("Indihome", "Biznet", "MNC Play", "First Media", "MyRepublic")
        val wifiAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, wifiList)

        wifiAutoComplete.setAdapter(wifiAdapter)
        wifiAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> wifiImageView.setImageResource(R.drawable.logoindihome)
                1 -> wifiImageView.setImageResource(R.drawable.biznet)
                2 -> wifiImageView.setImageResource(R.drawable.mncplay)
                3 -> wifiImageView.setImageResource(R.drawable.firstmedia)
                4 -> wifiImageView.setImageResource(R.drawable.myrepublic)
            }
        }

        backWifi.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }



    }
}