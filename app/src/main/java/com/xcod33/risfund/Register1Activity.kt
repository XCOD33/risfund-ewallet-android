package com.xcod33.risfund

import android.app.DatePickerDialog
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
import java.util.*

class Register1Activity : AppCompatActivity() {

    private lateinit var jenisKelaminRegisterAutoComplete: AutoCompleteTextView
    private lateinit var jenisKelaminRegisterInputLayout: TextInputLayout
    private lateinit var namaLengkapRegisterEditText: EditText
    private lateinit var namaInputLayout: TextInputLayout
    private lateinit var nomorTeleponRegisterEditText: EditText
    private lateinit var nomorTeleponInputLayout: TextInputLayout
    private lateinit var tanggalLahirRegisterEditText: EditText
    private lateinit var tanggalLahirRegisterInputLayout: TextInputLayout
    private lateinit var lanjutkanRegisterButton: Button
    private lateinit var daftarRegisterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)

        jenisKelaminRegisterAutoComplete = findViewById(R.id.jenisKelaminRegisterAutoComplete)
        jenisKelaminRegisterInputLayout = findViewById(R.id.jenisKelaminRegisterInputLayout)
        namaLengkapRegisterEditText = findViewById(R.id.namaLengkapRegisterEditText)
        namaInputLayout = findViewById(R.id.namaInputLayout)
        nomorTeleponRegisterEditText = findViewById(R.id.nomorTeleponRegisterEditText)
        nomorTeleponInputLayout = findViewById(R.id.nomorTeleponInputLayout)
        tanggalLahirRegisterInputLayout = findViewById(R.id.tanggalLahirRegisterInputLayout)
        tanggalLahirRegisterEditText = findViewById(R.id.tanggalLahirRegisterEditText)
        lanjutkanRegisterButton = findViewById(R.id.lanjutkanRegisterButton)
        daftarRegisterTextView = findViewById(R.id.daftarRegisterTextView)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val genderList = listOf("Laki - laki", "Perempuan")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderList)

        jenisKelaminRegisterAutoComplete.setAdapter(genderAdapter)
        jenisKelaminRegisterAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            jenisKelaminRegisterInputLayout.isHintEnabled = false
        }

        tanggalLahirRegisterEditText.setOnClickListener {
            tanggalLahirRegisterInputLayout.isHintEnabled = false
            val dpd = DatePickerDialog(this, android.R.style.Theme_Material_Dialog,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mMonth1 = mMonth + 1
                tanggalLahirRegisterEditText.setText("" + mYear + "/" + mMonth1 + "/" + mDay)
            }, year, month, day)
            dpd.show()
        }

        val getGender = when (jenisKelaminRegisterAutoComplete.text.toString()){
            "Laki - laki" -> "Male"
            "Perempuan" -> "Female"
            else -> null
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
                val bundle = Bundle()
                bundle.putString("name", namaLengkapRegisterEditText.text.toString())
                bundle.putString("birthdate", tanggalLahirRegisterEditText.text.toString())
                bundle.putString("phoneNumber", nomorTeleponRegisterEditText.text.toString())
                bundle.putString("gender", jenisKelaminRegisterAutoComplete.text.toString())

                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

    }
}