package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
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

        balanceNotifPaymentTextView.isVisible = user!!.balance!! < 100000

        usernamePaymentTextView.text = "Hi, ${user!!.fullName}"
        balancePaymentTextView.text = "Rp${user!!.balance.toString()}"
        fullNamePaymentEditText.setText(transferTo)

        transferPaymentButton.setOnClickListener {
            if (nominalPaymentEditText.text.isNullOrEmpty()) {
                nominalPaymentEditText.error = "Harap masukkan nominal"
                nominalPaymentEditText.isFocusable = true
            } else if (nominalPaymentEditText.text.toString().toInt() > user!!.balance!!) {
                nominalPaymentEditText.error = "Maaf saldo anda tidak mencukupi"
                nominalPaymentEditText.isFocusable = true
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