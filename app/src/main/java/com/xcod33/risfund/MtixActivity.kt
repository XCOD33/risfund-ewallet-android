package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.xcod33.risfund.data.GetUserResponse

class MtixActivity : AppCompatActivity() {

    private lateinit var backMtix: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mtix)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")
        backMtix = findViewById(R.id.backMtix)

        backMtix.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }
    }
}