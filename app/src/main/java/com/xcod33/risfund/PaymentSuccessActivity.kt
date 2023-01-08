package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_payment_success.*
import kotlinx.android.synthetic.main.activity_transfer_success.*

class PaymentSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")


        paymentBerhasilButton.setOnClickListener {
            val intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        accessIntent()
    }

    private fun accessIntent() {
        val intent = intent
        val paymentId = intent.getStringExtra("paymentId")
        val amount = intent.getStringExtra("amount")

        paymentAmountTextView.text = "-${amount}"
        transferIdPaymentTextView.text = paymentId
    }
}