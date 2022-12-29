package com.xcod33.risfund

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton

class TopUpActivity : AppCompatActivity() {

    private lateinit var back: ImageButton
    private lateinit var TopupButton: Button
    private lateinit var nominalEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        back = findViewById(R.id.back)
        TopupButton = findViewById(R.id.TopupButton)
        nominalEditText = findViewById(R.id.nominalEditText)

        back.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        TopupButton.setOnClickListener{
            if(nominalEditText.text.isEmpty()) {
                nominalEditText.error = "Input Top Up Amount"
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"));
                startActivity(intent)
            }
            }
        }
    }
