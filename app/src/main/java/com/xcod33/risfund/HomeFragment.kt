package com.xcod33.risfund

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
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

        fullNameTextView.text = "Hi, ${user!!.fullName}"
        balanceTextView.text = "Rp${user!!.balance.toString()}"

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
}
