package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.xcod33.risfund.data.GetUserResponse

class PdamActivity : AppCompatActivity() {

    private lateinit var backPdam: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdam)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        backPdam = findViewById(R.id.backPdam)

        backPdam.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }
    }
}