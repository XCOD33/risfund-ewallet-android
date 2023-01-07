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
import androidx.annotation.RequiresApi
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.fragment_home.*

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
            startActivity(intent)
        }
    }
}
