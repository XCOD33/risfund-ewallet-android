package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import kotlinx.android.synthetic.main.activity_transfer_success.*

class TransferSuccessActivity : AppCompatActivity() {

    private lateinit var backTransferSukses: ImageButton
    private lateinit var transferBerhasilButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_success)

        backTransferSukses = findViewById(R.id.backTransferSukses)

        backTransferSukses.setOnClickListener{
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        transferBerhasilButton = findViewById(R.id.transferBerhasilButton)

        transferBerhasilButton.setOnClickListener{
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        accessIntent()
    }

    private fun accessIntent() {
        val intent = intent
        val transferId = intent.getStringExtra("transferId")
        val amount = intent.getStringExtra("amount")

        transferAmountTextView.text = amount
        transferIdTextView.text = transferId
    }
}