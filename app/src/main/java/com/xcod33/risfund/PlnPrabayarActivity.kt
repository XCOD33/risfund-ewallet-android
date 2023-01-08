package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import com.xcod33.risfund.data.GetUserResponse

class PlnPrabayarActivity : AppCompatActivity() {

    private lateinit var backPlnPrabayar : ImageButton
    private lateinit var plnPrabayarAutoComplete: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pln_prabayar)

        val user = intent.getParcelableExtra<GetUserResponse>("data user")

        backPlnPrabayar = findViewById(R.id.backPlnPrabayar)
        plnPrabayarAutoComplete = findViewById(R.id.plnPrabayarAutoComplete)
        val plnList = listOf("PLN Pascabayar", "PLN Prabayar")
        val plnAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, plnList)

        plnPrabayarAutoComplete.setAdapter(plnAdapter)
        plnPrabayarAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> startActivity(Intent(this, PlnPascabayarActivity::class.java))
            }
        }

        backPlnPrabayar.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("data user", user)
            startActivity(intent)
        }
    }
}