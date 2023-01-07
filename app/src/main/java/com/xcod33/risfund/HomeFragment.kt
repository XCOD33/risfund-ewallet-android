package com.xcod33.risfund

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent = requireActivity().intent
        val userId = intent.getStringExtra("userId")
        val fullName = intent.getStringExtra("fullName")
        val phoneNumber = intent.getStringExtra("phoneNumber")
        val birthdate = intent.getStringExtra("birthdate")
        val gender = intent.getStringExtra("gender")
        val username = intent.getStringExtra("username")
        val balance = intent.getStringExtra("balance")
        val userQr = intent.getStringExtra("userQr")

        fullNameTextView.text = "Hi, $fullName"
        balanceTextView.text = "Rp${balance}"

        topUpButton.setOnClickListener{
            val intent = Intent(activity, TopUpActivity::class.java)
            startActivity(intent)
        }
    }
}