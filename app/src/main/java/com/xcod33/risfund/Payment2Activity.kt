package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_payment2.*

class Payment2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment2)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        val intent = intent
        val userQr = intent.getStringExtra("userQr")
        val transferTo = intent.getStringExtra("transferTo")

        usernamePaymentTextView.text = "Hi, ${user!!.fullName}"
        balancePaymentTextView.text = "Rp${user!!.balance.toString()}"
        fullNamePaymentEditText.setText(transferTo)

        transferPaymentButton.setOnClickListener {
            if (nominalPaymentEditText.text.isNullOrEmpty()) {
                nominalPaymentInputLayout.error = "Harap masukkan nominal"
            } else if (nominalPaymentEditText.text.toString().toInt() > user!!.balance!!) {
                nominalPaymentInputLayout.error = "Maaf saldo anda tidak mencukupi"
            } else {
                val bundle = Bundle()
                bundle.putString("userQr", userQr)
                bundle.putString("transferTo", transferTo)
                bundle.putString("note", catatanPaymentEditText.text.toString())
                bundle.putInt("amount", nominalPaymentEditText.text.toString().toInt())

                val intent = Intent(this, PaymentConfirmationActivity::class.java)
                intent.putExtras(bundle)
                intent.putExtra("dataUser", user)
                startActivity(intent)
            }
        }
    }
}