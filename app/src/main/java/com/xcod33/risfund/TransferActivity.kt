package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class TransferActivity : AppCompatActivity() {

    private lateinit var backTransfer: ImageButton
    private lateinit var transferButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        backTransfer = findViewById(R.id.backTransfer)
        transferButton = findViewById(R.id.TransferButton)

        backTransfer.setOnClickListener {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        transferButton.setOnClickListener{
            var intent = Intent(this, TransferConfirmationActivity::class.java)
            startActivity(intent)
        }
    }
}