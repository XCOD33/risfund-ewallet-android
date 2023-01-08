package com.xcod33.risfund

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetPaymentChannel
import kotlinx.android.synthetic.main.activity_list_bank.*
import kotlinx.android.synthetic.main.fragment_history.*
import org.json.JSONException
import org.json.JSONObject

class HistoryFragment : Fragment() {
    private var itemsViewModelRiwayat = ArrayList<ItemsViewModelRiwayat>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listHistory()
    }

    private fun listHistory() {
        val sessionManager = SessionManager(requireActivity().applicationContext)
        val token = JSONObject(sessionManager.getToken())

        AndroidNetworking.get("https://risfund.loophole.site/api/history")
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(com.androidnetworking.common.Priority.MEDIUM)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if (response != null) {
                            if (response.getString("message").equals("History user found")) {
                                val data = response.getJSONArray("data")
                                itemsViewModelRiwayat.clear()

                                for (i in 0 until data.length()) {
                                    val value = data.getJSONObject(i)
                                    val type = value.getString("transactionType")
                                    val date = value.getString("created_at")
                                    val amount = value.getString("amount")

                                    val responsesHistory = ItemsViewModelRiwayat(type, date, amount.toInt())
                                    itemsViewModelRiwayat.add(responsesHistory)
                                }
                            }
                        }
                        riwayatRecyclerView.adapter = CustomAdapterRiwayat(itemsViewModelRiwayat)
                        val layoutManager = LinearLayoutManager(activity)
                        riwayatRecyclerView.layoutManager = layoutManager
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                override fun onError(anError: ANError?) {
                }
            })
    }
}