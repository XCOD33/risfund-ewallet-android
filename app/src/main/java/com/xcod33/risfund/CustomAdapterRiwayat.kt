package com.xcod33.risfund

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterRiwayat(private val mList: ArrayList<ItemsViewModelRiwayat>) : RecyclerView.Adapter<CustomAdapterRiwayat.ViewHolder>(){

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val imageViewCardViewRiwayat: ImageView = ItemView.findViewById(R.id.imageViewCardViewRiwayat)
        val judulTextViewCardViewRiwayat: TextView = ItemView.findViewById(R.id.judulTextViewCardViewRiwayat)
        val tanggalTextViewCardViewRiwayat: TextView = ItemView.findViewById(R.id.tanggalTextViewCardViewRiwayat)
        val hargaTextViewCardViewRiwayat: TextView = ItemView.findViewById(R.id.hargaTextViewCardViewRiwayat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_riwayat, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]

        holder.imageViewCardViewRiwayat.setImageResource(itemsViewModel.getImage as Int)
        holder.judulTextViewCardViewRiwayat.text = itemsViewModel.getTitle
        holder.tanggalTextViewCardViewRiwayat.text = itemsViewModel.getDate
        holder.hargaTextViewCardViewRiwayat.text = itemsViewModel.getPrice
        }

    override fun getItemCount(): Int {
        return mList.size
    }
}