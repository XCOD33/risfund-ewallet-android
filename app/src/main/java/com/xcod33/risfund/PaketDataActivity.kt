package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_paket_data.*

class PaketDataActivity : AppCompatActivity() {

    private lateinit var backPaketData: ImageButton
    private lateinit var providerPaketDataAutoComplete: AutoCompleteTextView
    private lateinit var providerPaketDataImageView: ImageView
    private lateinit var paketDataRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket_data)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        backPaketData = findViewById(R.id.backPaketData)
        providerPaketDataAutoComplete = findViewById(R.id.providerPaketDataAutoComplete)
        providerPaketDataImageView = findViewById(R.id.providerPaketDataImageView)
        paketDataRecyclerView = findViewById(R.id.paketDataRecyclerView)

        val providerList = listOf("Telkomsel", "Tri", "XL", "Axis", "Smartfren")
        val providerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, providerList)

        providerPaketDataAutoComplete.setAdapter(providerAdapter)
        providerPaketDataAutoComplete.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> providerPaketDataImageView.setImageResource(R.drawable.telkomsel)
                1 -> providerPaketDataImageView.setImageResource(R.drawable.tri)
                2 -> providerPaketDataImageView.setImageResource(R.drawable.xl)
                3 -> providerPaketDataImageView.setImageResource(R.drawable.axis)
                4 -> providerPaketDataImageView.setImageResource(R.drawable.smartfren)
            }
        }

        backPaketData.setOnClickListener {
            var intent = Intent(this, HomeActivity2::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        paketDataRecyclerView.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ItemsViewModelPaketData>()
        for(i in 1..7) {
            data.add(ItemsViewModelPaketData(i, i))
        }

        var phoneNumber: String
        var provider: String

        paketDataRecyclerView.setOnClickListener {
            phoneNumber = nomorTeleponPaketDataEditText.text.toString()
            provider = providerPaketDataAutoComplete.text.toString()

            Log.d("phoneNumber", phoneNumber)

            val adapter = CustomAdapterPaketData(data, phoneNumber, provider, user, this)
            paketDataRecyclerView.adapter = adapter
        }

        val adapter = CustomAdapterPaketData(data, nomorTeleponPaketDataEditText.text.toString(), providerPaketDataAutoComplete.text.toString(), user, this)
        paketDataRecyclerView.adapter = adapter
    }
}