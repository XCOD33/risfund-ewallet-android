package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_pdam.*

class PdamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdam)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        backPdam.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        pdamButton.setOnClickListener {
            createTransaction(user)
        }
    }

    private fun createTransaction(user: GetUserResponse?) {

    }
}