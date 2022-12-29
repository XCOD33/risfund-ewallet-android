package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class PlnPrabayarActivity : AppCompatActivity() {

    private lateinit var backPlnPrabayar : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pln_prabayar)

        backPlnPrabayar = findViewById(R.id.backPlnPrabayar)

        backPlnPrabayar.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}