package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class PulsaActivity : AppCompatActivity() {

    private lateinit var backPulsa: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulsa)

        backPulsa = findViewById(R.id.backPulsa)

        backPulsa.setOnClickListener{
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }


}