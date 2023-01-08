package com.xcod33.risfund

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_transfer.*
import org.json.JSONException
import org.json.JSONObject
import java.util.jar.Manifest

class PaymentActivity : AppCompatActivity() {

    private lateinit var codescanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        backPayment.setOnClickListener {
            val intent = Intent(this, HomeActivity2::class.java)
            startActivity(intent)
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 123)
        } else {
            startScanning()
        }

    }

    private fun startScanning() {
        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        val scannerView: CodeScannerView = findViewById(R.id.scanner_view)
        codescanner = CodeScanner(this, scannerView)
        codescanner.camera = CodeScanner.CAMERA_BACK
        codescanner.formats = CodeScanner.ALL_FORMATS

        codescanner.autoFocusMode = AutoFocusMode.SAFE
        codescanner.scanMode = ScanMode.SINGLE
        codescanner.isAutoFocusEnabled = true
        codescanner.isFlashEnabled = false

        codescanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val jobj = JSONObject()
                try {
                    jobj.put("userQr", it.text.toString().trim())
                } catch (e: JSONException) {
                    Log.e("Ejobj", e.toString())
                }

                val sessionManager = SessionManager(this)
                val token = JSONObject(sessionManager.getToken())

                AndroidNetworking.post("https://risfund.loophole.site/api/check-user-qr")
                    .addJSONObjectBody(jobj)
                    .addHeaders("Accept", "application/json")
                    .addHeaders("Authorization", "Bearer " + token.getString("token"))
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(object: JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject?) {
                            if(response!!.getString("message").equals("User Found")) {
                                val data = JSONObject(response.getString("data"))
                                val fullName = data.getString("fullName")

                                Log.d("data", data.toString())

                                val intent = Intent(this@PaymentActivity, Payment2Activity::class.java)
                                intent.putExtra("transferTo", fullName)
                                intent.putExtra("userQr", it.text.toString())
                                intent.putExtra("dataUser", user)
                                startActivity(intent)
                            }
                        }

                        override fun onError(anError: ANError?) {
                            if(anError!!.errorCode != 0) {
                                val response = JSONObject(anError.errorBody)
                                when(response.getString("message")) {
                                    "User not found" -> noHandphoneTransferInputLayout.error = response.getString("message")
                                    "Error check" -> noHandphoneTransferInputLayout.error = JSONObject(response.getString("data")).getString("error")
                                }
                            }
                        }
                    })
            }
        }

        codescanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Kamera initilization error = ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        scannerView.setOnClickListener {
            codescanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            } else{
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codescanner.isInitialized){
            codescanner?.startPreview()
        }
    }

    override fun onPause() {
        if (::codescanner.isInitialized){
            codescanner?.releaseResources()
        }
        super.onPause()
    }
}