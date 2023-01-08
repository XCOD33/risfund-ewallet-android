package com.xcod33.risfund

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetPaymentChannel
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_list_bank.*
import org.json.JSONException
import org.json.JSONObject

class ListBankActivity : AppCompatActivity() {

    private var listPaymentChannel = ArrayList<GetPaymentChannel>()
    private lateinit var customAdapter: CustomAdapterListBank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_bank)

        choosePaymentChannel(this)
    }

    private fun choosePaymentChannel(context: Context) {
        AndroidNetworking.get("https://risfund.loophole.site/api/payment-channel")
            .addHeaders("Accept", "application/json")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if (response!!.getString("success").equals(true.toString())) {
                            val data = response.getJSONArray("data")

                            listPaymentChannel.clear()

                            for (i in 0 until data.length()) {
                                val value = data.getJSONObject(i)
                                val code = value.getString("code")
                                val name = value.getString("name")
                                val iconUrl = value.getString("icon_url")

                                val responses = GetPaymentChannel(iconUrl, code, name)

                                listPaymentChannel.add(responses)
                            }
                        }

                        listBankRecyclerView.adapter = CustomAdapterListBank(listPaymentChannel, context)
                        val layoutManager = LinearLayoutManager(applicationContext)
                        listBankRecyclerView.layoutManager = layoutManager
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(anError: ANError?) {

                }
            })
    }
}