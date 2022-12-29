package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class PlnPascabayarActivity : AppCompatActivity() {

    private lateinit var backPlnPascabayar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pln_pascabayar)

        backPlnPascabayar = findViewById(R.id.backPlnPascabayar)

        backPlnPascabayar.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}