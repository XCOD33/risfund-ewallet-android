package com.xcod33.risfund

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.xcod33.risfund.data.GetPaymentChannel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class CustomAdapterListBank(private val mListBank: ArrayList<GetPaymentChannel>): RecyclerView.Adapter<CustomAdapterListBank.ViewHolder>() {
    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val bankImageView: ImageView = ItemView.findViewById(R.id.bankImageView)
        val bankTextView: TextView = ItemView.findViewById(R.id.bankTextView)
        val containerPayment: LinearLayout = ItemView.findViewById(R.id.layoutCardListBank1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_listbank, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mListBank[position]

//        holder.bankImageView.setImageResource(itemsViewModel.iconUrl!!.toString().toInt())
        Glide.with(holder.bankImageView).load(itemsViewModel.iconUrl).into(holder.bankImageView)
        holder.bankTextView.text = itemsViewModel.name

        holder.containerPayment.setOnClickListener {
            val intent = Intent(holder.itemView.context, TripayWebViewActivity::class.java)
            intent.putExtra("dataPayment", itemsViewModel.code)
            holder.itemView.context.startActivity(intent)

        }
    }



    override fun getItemCount(): Int {
        return mListBank.size
    }
}