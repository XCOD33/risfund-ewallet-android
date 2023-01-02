package com.xcod33.risfund

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterVoucher(private val mList: List<ItemsViewModelVoucher>) : RecyclerView.Adapter<CustomAdapterVoucher.ViewHolder>() {

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val purchaseTextView: TextView = ItemView.findViewById(R.id.purchaseTextView)
        val hargaPurchaseTextView: TextView = ItemView.findViewById(R.id.hargaPurchaseTextView)
        val purchaseImageButton: ImageButton = ItemView.findViewById(R.id.purchaseImageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_purchases, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]

        holder.purchaseTextView.text = itemsViewModel.getTitle
        holder.hargaPurchaseTextView.text = itemsViewModel.getPrice
        holder.purchaseImageButton.setOnClickListener {
            val intent = Intent(holder.purchaseImageButton.context, PaymentConfirmationActivity::class.java)
            holder.purchaseImageButton.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}