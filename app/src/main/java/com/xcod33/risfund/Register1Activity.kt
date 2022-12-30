package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Register1Activity : AppCompatActivity() {

    private lateinit var jenisKelaminRegisterAutoComplete: AutoCompleteTextView
    private lateinit var jenisKelaminRegisterInputLayout: TextInputLayout
    private lateinit var namaLengkapRegisterEditText: EditText
    private lateinit var namaInputLayout: TextInputLayout
    private lateinit var nomorTeleponRegisterEditText: EditText
    private lateinit var nomorTeleponInputLayout: TextInputLayout
    private lateinit var tanggalLahirRegisterEditText: EditText
    private lateinit var tanggalLahirInputLayout: TextInputLayout
    private lateinit var lanjutkanRegisterButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)

        jenisKelaminRegisterAutoComplete = findViewById(R.id.jenisKelaminRegisterAutoComplete)
        jenisKelaminRegisterInputLayout = findViewById(R.id.jenisKelaminRegisterInputLayout)
        namaLengkapRegisterEditText = findViewById(R.id.namaLengkapRegisterEditText)
        namaInputLayout = findViewById(R.id.namaInputLayout)
        nomorTeleponRegisterEditText = findViewById(R.id.nomorTeleponRegisterEditText)
        nomorTeleponInputLayout = findViewById(R.id.nomorTeleponInputLayout)
        tanggalLahirRegisterEditText = findViewById(R.id.tanggalLahirRegisterEditText)
        lanjutkanRegisterButton = findViewById(R.id.lanjutkanRegisterButton)

        val genderList = listOf("Laki - laki", "Perempuan")
        val bundle = Bundle()
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderList)


        jenisKelaminRegisterAutoComplete.setAdapter(genderAdapter)
        jenisKelaminRegisterAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            jenisKelaminRegisterInputLayout.isHintEnabled = false
        }

        lanjutkanRegisterButton.setOnClickListener {
            if (namaLengkapRegisterEditText.text.isEmpty()) {
                namaLengkapRegisterEditText.error = "Nama diperlukan"
                namaLengkapRegisterEditText.requestFocus()
            } else if (nomorTeleponRegisterEditText.text.isEmpty()) {
                nomorTeleponRegisterEditText.error = "Nomor Telepon diperlukan"
                nomorTeleponRegisterEditText.requestFocus()
            } else if (jenisKelaminRegisterAutoComplete.text.isEmpty()) {
                jenisKelaminRegisterInputLayout.error = "Jenis Kelamin diperlukan"
                tanggalLahirRegisterEditText.error = "Tanggal Lahir diperlukan"
                tanggalLahirRegisterEditText.requestFocus()
            } else {
                val intent = Intent(this, Register2Activity::class.java)
                bundle.putString("name", namaLengkapRegisterEditText.text.toString())
                bundle.putString("phoneNumber", nomorTeleponRegisterEditText.text.toString())
                bundle.putString("gender", jenisKelaminRegisterAutoComplete.text.toString())

                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

    }
}