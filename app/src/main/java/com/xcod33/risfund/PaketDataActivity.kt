package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaketDataActivity : AppCompatActivity() {

    private lateinit var backPaketData: ImageButton
    private lateinit var providerPaketDataAutoComplete: AutoCompleteTextView
    private lateinit var providerPaketDataImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket_data)

        backPaketData = findViewById(R.id.backPaketData)
        providerPaketDataAutoComplete = findViewById(R.id.providerPaketDataAutoComplete)
        providerPaketDataImageView = findViewById(R.id.providerPaketDataImageView)
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
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        //        getting the recycler view by its id
        val recyclerview = findViewById<RecyclerView>(R.id.containerRecyclerView)

//        this creates a vertical layout manager
        recyclerview.layoutManager = LinearLayoutManager(this)

//        ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModelPaketData>()

//        This loop will create 9 Views containing
//        the image with the count of view
        for(i in 1..9) {
            data.add(ItemsViewModelPaketData(i, i, i, i))
        }

//        This will pass the ArrayList to our Adapter
        val adapter = CustomAdapterPaketData(data)

//        Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }
}