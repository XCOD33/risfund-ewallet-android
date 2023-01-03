package com.xcod33.risfund

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TransferConfirmationActivity : AppCompatActivity() {

    private lateinit var transferConfirmationRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_confirmation)

        transferConfirmationRecyclerView = findViewById(R.id.transferConfirmationRecyclerView)

        transferConfirmationRecyclerView.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ItemsViewModelTransferConfirmation>()
        for(i in 1..5) {
            data.add(ItemsViewModelTransferConfirmation(i, i))
        }

        val adapter = CustomAdapterTransferConfirmation(data)
        transferConfirmationRecyclerView.adapter = adapter
    }
}