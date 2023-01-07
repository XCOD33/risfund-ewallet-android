package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_payment2.*
import kotlinx.android.synthetic.main.activity_transfer.*
import org.json.JSONException
import org.json.JSONObject

class Payment2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment2)

        val intent = intent
        val transferTo = intent.getStringExtra("transferTo")
        fullNamePaymentEditText.setText(transferTo)
    }
}