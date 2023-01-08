package com.xcod33.risfund

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.balanceTextView
import kotlinx.android.synthetic.main.fragment_home.bpjsButton
import kotlinx.android.synthetic.main.fragment_home.mtixButton
import kotlinx.android.synthetic.main.fragment_home.paketDataButton
import kotlinx.android.synthetic.main.fragment_home.paymentButton
import kotlinx.android.synthetic.main.fragment_home.pdamButton
import kotlinx.android.synthetic.main.fragment_home.plnButton
import kotlinx.android.synthetic.main.fragment_home.pulsaButton
import kotlinx.android.synthetic.main.fragment_home.topUpButton
import kotlinx.android.synthetic.main.fragment_home.transferButton
import kotlinx.android.synthetic.main.fragment_home.voucherButton
import kotlinx.android.synthetic.main.fragment_home.wifiButton
import org.json.JSONException
import org.json.JSONObject

class HomeFragment : Fragment() {

    private val userList = ArrayList<GetUserResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = requireActivity().intent.getParcelableExtra<GetUserResponse>("dataUser")

        if (user!!.balance!! >= 1000000) {
            balanceTextView.textSize = 28.0f
        }

        fullNameTextView.text = "Hi, ${user!!.fullName}"
        balanceTextView.text = "Rp${user!!.balance.toString()}"

        balanceNotifHomeTextView.isVisible = user.balance!! < 100000

        homeRefresh.setOnRefreshListener {
            refreshData()
        }

        topUpButton.setOnClickListener{
            val intent = Intent(activity, TopUpActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        transferButton.setOnClickListener {
            val intent = Intent(activity, TransferActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        paymentButton.setOnClickListener {
            val intent = Intent(activity, PaymentActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        pulsaButton.setOnClickListener {
            val intent = Intent(activity, PulsaActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        paketDataButton.setOnClickListener {
            val intent = Intent(activity, PaketDataActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        voucherButton.setOnClickListener {
            val intent = Intent(activity, VoucherFisikActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        plnButton.setOnClickListener {
            val intent = Intent(activity, PlnPascabayarActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        pdamButton.setOnClickListener {
            val intent = Intent(activity, PdamActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        wifiButton.setOnClickListener {
            val intent = Intent(activity, WifiActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        mtixButton.setOnClickListener {
            val intent = Intent(activity, MtixActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }

        bpjsButton.setOnClickListener {
            var intent = Intent(activity, BpjsActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }
    }

    private fun refreshData() {
        val sessionManager = SessionManager(requireActivity().applicationContext)
        val token = JSONObject(sessionManager.getToken())
        AndroidNetworking.get("https://risfund.loophole.site/api/user")
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if (response != null) {
                            if(response.getString("message").equals("User found")) {
                                val data = JSONObject(response.getString("data"))

                                Log.d("token", token.getString("token"))

                                val userId = data.getString("userId")
                                val fullName = data.getString("fullName")
                                val phoneNumber = data.getString("phoneNumber")
                                val birthdate = data.getString("birthdate")
                                val gender = data.getString("gender")
                                val username = data.getString("username")
                                val balance = data.getString("balance")
                                val userQr = data.getString("userQr")

                                userList.clear()

                                val responses = GetUserResponse(userId.toInt(), fullName, phoneNumber, birthdate, gender, username, balance.toInt(), userQr)

                                userList.add(responses)

                                val intent = Intent(requireContext().applicationContext, HomeActivity2::class.java)
                                intent.putExtra("dataUser", responses)
                                startActivity(intent)
                            }
                        }
                    } catch (error: JSONException) {
                        Log.d("error response", error.toString())
                    }
                }

                override fun onError(error: ANError?) {
                    if (error != null) {
                        if (error.errorCode != 0) {
                            val response = JSONObject(error.errorBody)
//                                Log.e("responseError", response.getString("message"))
                            Toast.makeText(requireActivity().applicationContext, response.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
    }
}
