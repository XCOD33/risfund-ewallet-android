package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView

class PulsaActivity : AppCompatActivity() {

    private lateinit var backPulsa: ImageButton
    private lateinit var providerPulsaImageView: ImageView
    private lateinit var providerPulsaAutoComplete: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulsa)

        backPulsa = findViewById(R.id.backPulsa)
        providerPulsaImageView = findViewById(R.id.providerPulsaImageView)
        providerPulsaAutoComplete = findViewById(R.id.providerPulsaAutoComplete)
        val providerList = listOf("Telkomsel", "Tri", "XL", "Axis", "Smartfren")
        val providerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, providerList)

        providerPulsaAutoComplete.setAdapter(providerAdapter)
        providerPulsaAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> providerPulsaImageView.setImageResource(R.drawable.telkomsel)
                1 -> providerPulsaImageView.setImageResource(R.drawable.tri)
                2 -> providerPulsaImageView.setImageResource(R.drawable.xl)
                3 -> providerPulsaImageView.setImageResource(R.drawable.axis)
                4 -> providerPulsaImageView.setImageResource(R.drawable.smartfren)
            }
        }

        backPulsa.setOnClickListener{
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }


}