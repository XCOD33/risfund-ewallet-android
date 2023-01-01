package com.xcod33.risfund

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var bottomNav: com.google.android.material.bottomnavigation.BottomNavigationView
    private lateinit var editProfilButton: Button
    private lateinit var namaLengkapProfilEditText: TextInputEditText
    private lateinit var nomorTeleponProfilEditText: TextInputEditText
    private lateinit var tanggalLahirProfilEditText: TextInputEditText
    private lateinit var tanggalLahirProfilInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        bottomNav = findViewById(R.id.bottomNav)
        editProfilButton = findViewById(R.id.editProfilButton)
        namaLengkapProfilEditText = findViewById(R.id.namaLengkapProfilEditText)
        nomorTeleponProfilEditText = findViewById(R.id.nomorTeleponProfilEditText)
        tanggalLahirProfilEditText = findViewById(R.id.tanggalLahirProfilEditText)
        tanggalLahirProfilInputLayout = findViewById(R.id.tanggalLahirProfilInputLayout)
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        bottomNav.setSelectedItemId(R.id.settingNavigation);

        tanggalLahirProfilEditText.setOnClickListener {
            tanggalLahirProfilInputLayout.isHintEnabled = false
            val dpd = DatePickerDialog(this, android.R.style.Theme_Material_Dialog,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mMonth1 = mMonth + 1
                tanggalLahirProfilEditText.setText("" + mYear + "/" + mMonth1 + "/" + mDay)
            }, year, month, day)
            dpd.show()
        }

        editProfilButton.setOnClickListener {
            namaLengkapProfilEditText.setText(namaLengkapProfilEditText.text.toString())
            nomorTeleponProfilEditText.setText(nomorTeleponProfilEditText.text.toString())
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeNavigation -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.qrNavigation-> {
                    val intent = Intent(this, MyQrActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.historyNavigation -> {
                    val intent = Intent(this, HistoryActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.settingNavigation -> {
                    true
                }

                else -> false
            }
        }
    }
}