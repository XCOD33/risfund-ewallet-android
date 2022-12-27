package com.xcod33.risfund

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class Register1Activity : AppCompatActivity() {

    private lateinit var jenisKelaminRegisterAutoComplete: AutoCompleteTextView
    private lateinit var jenisKelaminRegisterInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)

        jenisKelaminRegisterAutoComplete = findViewById(R.id.jenisKelaminRegisterAutoComplete)
        jenisKelaminRegisterInputLayout = findViewById(R.id.jenisKelaminRegisterInputLayout)

        val gender = listOf("Laki - laki", "Perempuan")

        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gender)

        jenisKelaminRegisterAutoComplete.setAdapter(genderAdapter)
        jenisKelaminRegisterAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            jenisKelaminRegisterInputLayout.setHint(adapterView.getItemAtPosition(i).toString())

        }
    }
}