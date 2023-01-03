package com.xcod33.risfund

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapterTransferConfirmation(private val mList: ArrayList<ItemsViewModelTransferConfirmation>) : RecyclerView.Adapter<CustomAdapterTransferConfirmation.ViewHolder>(){

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val judulTextViewCardViewConfirmation: TextView = ItemView.findViewById(R.id.judulTextViewCardViewConfirmation)
        val detailTextViewCardViewConfirmation: TextView = ItemView.findViewById(R.id.detailTextViewCardViewConfirmation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapterTransferConfirmation.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_confirmation, parent, false)

        return CustomAdapterTransferConfirmation.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapterTransferConfirmation.ViewHolder, position: Int) {
        val itemsViewModel = mList[position]

        holder.judulTextViewCardViewConfirmation.text = itemsViewModel.getTitle
        holder.detailTextViewCardViewConfirmation.text = itemsViewModel.getDetail
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}