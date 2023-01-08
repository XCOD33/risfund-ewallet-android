package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import com.xcod33.risfund.data.GetUserResponse

class PlnPascabayarActivity : AppCompatActivity() {

    private lateinit var backPlnPascabayar: ImageButton
    private lateinit var plnPascabayarAutoComplete: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pln_pascabayar)

        val user = intent.getParcelableExtra<GetUserResponse>("data user")

        backPlnPascabayar = findViewById(R.id.backPlnPascabayar)
        plnPascabayarAutoComplete = findViewById(R.id.plnPascabayarAutoComplete)

        val plnList = listOf("PLN Pascabayar", "PLN Prabayar")
        val plnAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, plnList)

        plnPascabayarAutoComplete.setAdapter(plnAdapter)
        plnPascabayarAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                1 -> startActivity(Intent(this, PlnPrabayarActivity::class.java))
            }
        }

        backPlnPascabayar.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("data user", user)
            startActivity(intent)
        }
    }
}