package com.xcod33.risfund

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class Register2Activity : AppCompatActivity() {

    private lateinit var usernameRegisterInputLayout: TextInputLayout
    private lateinit var usernameRegisterEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)


        val bundle = intent.extras
        val gender = bundle?.getString("gender")


    }
}