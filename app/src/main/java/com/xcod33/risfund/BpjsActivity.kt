package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.xcod33.risfund.data.GetUserResponse

class BpjsActivity : AppCompatActivity() {

    private lateinit var backBpjs: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bpjs)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        backBpjs = findViewById(R.id.backBpjs)

        backBpjs.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }
    }
}