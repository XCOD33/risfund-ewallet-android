package com.xcod33.risfund

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterListBank(private val mListBank: ArrayList<ItemsViewModelBank>): RecyclerView.Adapter<CustomAdapterListBank.ViewHolder>() {
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val bankImageView: ImageView = ItemView.findViewById(R.id.bankImageView)
        val bankTextView: TextView = ItemView.findViewById(R.id.bankTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_listbank, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mListBank[position]

        holder.bankImageView.setImageResource(itemsViewModel.getImage)
        holder.bankTextView.text = itemsViewModel.getBankName
    }

    override fun getItemCount(): Int {
        return mListBank.size
    }
}