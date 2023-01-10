package com.xcod33.risfund

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.facebook.shimmer.ShimmerFrameLayout
import com.xcod33.risfund.data.GetPaymentChannel
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_list_bank.*
import kotlinx.android.synthetic.main.card_view_listbank_placeholder.*
import org.json.JSONException
import org.json.JSONObject

class ListBankActivity : AppCompatActivity() {

    private var listPaymentChannel = ArrayList<GetPaymentChannel>()
    private lateinit var customAdapter: CustomAdapterListBank
    private lateinit var shimmer: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_bank)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        shimmer = findViewById(R.id.shimmerBank)
        shimmer.startShimmer()

        choosePaymentChannel(this)

        backListBank.setOnClickListener {
            val intent = Intent(this, TopUpActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }
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

                        shimmer.stopShimmer()
                        shimmer.visibility = View.GONE

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