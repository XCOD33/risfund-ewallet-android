package com.xcod33.risfund

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xcod33.risfund.data.GetUserResponse
import com.xcod33.risfund.databinding.ActivityHome2Binding
import kotlinx.android.synthetic.main.activity_home2.*
import org.json.JSONException
import org.json.JSONObject

class HomeActivity2 : AppCompatActivity() {

    private val userList = ArrayList<GetUserResponse>()

    private lateinit var bottomNav2: BottomNavigationView
    private lateinit var binding: ActivityHome2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())


        binding.bottomNav2.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeNavigation -> replaceFragment(HomeFragment())
                R.id.qrNavigation -> replaceFragment(MyQrFragment())
                R.id.historyNavigation -> replaceFragment(HistoryFragment())
                R.id.settingNavigation -> replaceFragment(SettingFragment())
            }
            true
        }
    }

    private fun refreshData() {
        val sessionManager = SessionManager(this)
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

                                val intent = Intent(this@HomeActivity2, HomeActivity2::class.java)
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
                            Toast.makeText(this@HomeActivity2, response.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
    }

    private fun  replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}