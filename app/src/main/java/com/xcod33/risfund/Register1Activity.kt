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
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import kotlinx.android.synthetic.main.activity_register1.*

class Register1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val genderList = listOf("Laki - laki", "Perempuan")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderList)
        var getGender: String? = null

        jenisKelaminRegisterAutoComplete.setAdapter(genderAdapter)
        jenisKelaminRegisterAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            jenisKelaminRegisterInputLayout.isHintEnabled = false
            when (i) {
                0 -> getGender = "male"
                1 -> getGender = "female"
            }
        }

        tanggalLahirRegisterEditText.setOnClickListener {
            tanggalLahirRegisterInputLayout.isHintEnabled = false
            val dpd = DatePickerDialog(this, android.R.style.Theme_Material_Dialog,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mMonth1 = mMonth + 1
                tanggalLahirRegisterEditText.setText("" + mYear + "/" + mMonth1 + "/" + mDay)
            }, year, month, day)
            dpd.show()
        }

        lanjutkanRegisterButton.setOnClickListener {
            if (namaLengkapRegisterEditText.text!!.isEmpty()) {
                namaLengkapRegisterEditText.error = "Nama diperlukan"
                namaLengkapRegisterEditText.requestFocus()
            } else if (nomorTeleponRegisterEditText.text!!.isEmpty()) {
                nomorTeleponRegisterEditText.error = "Nomor Telepon diperlukan"
                nomorTeleponRegisterEditText.requestFocus()
            } else if (tanggalLahirRegisterEditText.text.isNullOrEmpty()) {
                tanggalLahirRegisterEditText.error = "Tanggal Lahir diperlukan"
                tanggalLahirRegisterEditText.requestFocus()
            } else if (jenisKelaminRegisterAutoComplete.text.isEmpty()) {
                jenisKelaminRegisterAutoComplete.error = "Jenis Kelamin diperlukan"
                jenisKelaminRegisterAutoComplete.requestFocus()
            } else {
                val intent = Intent(this, Register2Activity::class.java)
                val bundle = Bundle()
                bundle.putString("name", namaLengkapRegisterEditText.text.toString())
                bundle.putString("birthdate", tanggalLahirRegisterEditText.text.toString())
                bundle.putString("phoneNumber", nomorTeleponRegisterEditText.text.toString())
                bundle.putString("gender", getGender.toString())

                intent.putExtras(bundle)
                startActivity(intent)
            }
        }

    }
}