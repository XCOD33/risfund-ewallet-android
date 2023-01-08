package com.xcod33.risfund

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import org.json.JSONException
import org.json.JSONObject

class CustomAdapterPaketData(private val mList: ArrayList<ItemsViewModelPaketData>, private val phoneNumber: String?, private val provider: String?, private val user: GetUserResponse?, private val context: Context) : RecyclerView.Adapter<CustomAdapterPaketData.ViewHolder>() {

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {
        val purchaseTextView: TextView = ItemView.findViewById(R.id.purchaseTextView)
        val hargaPurchaseTextView: TextView = ItemView.findViewById(R.id.hargaPurchaseTextView)
        val purchaseImageButton: ImageButton = ItemView.findViewById(R.id.purchaseImageButton)
        val layout1: LinearLayout = ItemView.findViewById(R.id.layout1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_purchases, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]

        holder.purchaseTextView.text = itemsViewModel.getTitle
        holder.hargaPurchaseTextView.text = itemsViewModel.getPrice.toString()

        holder.layout1.setOnClickListener {
            Log.d("phoneNumber", phoneNumber.toString())
            Log.d("provider", provider.toString())
            createTransaction(holder.purchaseTextView.text.toString(), holder.hargaPurchaseTextView.text.toString().toInt(), holder.layout1.context)
        }
    }

    private fun createTransaction(plan: String?, amount: Int?, context: Context) {
        val sessionManager = SessionManager(context)
        val token = JSONObject(sessionManager.getToken())

        val jobj = JSONObject()
        try {
            jobj.put("phoneNumber", phoneNumber)
            jobj.put("provider", provider)
            jobj.put("plan", plan)
            jobj.put("amount", amount)
        } catch (e:JSONException) {
            e.printStackTrace()
        }

        AndroidNetworking.post("https://risfund.loophole.site/api/purchase/pulsa")
            .addJSONObjectBody(jobj)
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if(response!!.getString("message").equals("Purchase paket data succeeded")) {
                            val data = JSONObject(response.getString("data"))
                            val purchaseType = data.getString("purchaseType")
                            val amount = data.getString("amount")

                            val bundle = Bundle()
                            bundle.putString("type", "paket data")
                            bundle.putString("purchaseType", purchaseType)
                            bundle.putString("amount", amount)

                            val intent = Intent(context, PurchasesSuccessActivity::class.java)
                            intent.putExtras(bundle)
                            intent.putExtra("dataUser", user)
                            context.startActivity(intent)
                        }
                    } catch (e: JSONException) {
                        Log.e("eResponse", e.toString())
                    }
                }

                override fun onError(anError: ANError?) {
                    if(anError!!.errorCode != 0) {
                        val response = JSONObject(anError.errorBody)

                        Toast.makeText(context, response.getString("message"), Toast.LENGTH_LONG).show()
                    } else {
                        anError.toString()
                    }
                }
            })
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}