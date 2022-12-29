package com.xcod33.risfund

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class Register2Activity : AppCompatActivity() {

    private lateinit var usernameRegisterInputLayout: TextInputLayout
    private lateinit var usernameRegisterEditText: EditText
    private lateinit var passwordRegisterInputLayout: TextInputLayout
    private lateinit var passwordRegisterEditText: EditText
    private lateinit var rePasswordRegisterInputLayout: TextInputLayout
    private lateinit var rePasswordRegisterEditText: EditText
    private lateinit var daftarRegisterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)


        val bundle = intent.extras
        val name = bundle?.getString("name")
        val phoneNumber = bundle?.getString("phoneNumber")
        val gender = bundle?.getString("gender")

        usernameRegisterInputLayout = findViewById(R.id.usernameRegisterInputLayout)
        usernameRegisterEditText = findViewById(R.id.usernameRegisterEditText)
        passwordRegisterInputLayout = findViewById(R.id.passwordRegisterInputLayout)
        passwordRegisterEditText = findViewById(R.id.passwordRegisterEditText)
        rePasswordRegisterInputLayout = findViewById(R.id.rePasswordRegisterInputLayout)
        rePasswordRegisterEditText = findViewById(R.id.rePasswordRegisterEditText)
        daftarRegisterButton = findViewById(R.id.daftarRegisterButton)

        daftarRegisterButton.setOnClickListener {
            if (usernameRegisterEditText.text.isEmpty()) {

            }
        }

    }
}