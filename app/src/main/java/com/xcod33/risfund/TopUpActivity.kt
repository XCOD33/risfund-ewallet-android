package com.xcod33.risfund

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class TopUpActivity : AppCompatActivity() {

    private lateinit var back: ImageButton
    private lateinit var TopupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        back = findViewById(R.id.back)
        TopupButton = findViewById(R.id.TopupButton)

        back.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        TopupButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
            startActivity(intent)
            }
        }
    }
