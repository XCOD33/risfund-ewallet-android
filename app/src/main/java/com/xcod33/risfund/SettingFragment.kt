package com.xcod33.risfund

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Priority
import com.xcod33.risfund.data.GetUserResponse
import kotlinx.android.synthetic.main.activity_register1.*
import kotlinx.android.synthetic.main.fragment_setting.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class SettingFragment : Fragment() {

    private val userList = ArrayList<GetUserResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = requireActivity().intent.getParcelableExtra<GetUserResponse>("dataUser")

        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        namaPenggunaProfilTextView.setText(user!!.fullName)
        namaLengkapProfilEditText.setText(user.fullName)
        usernameEditText.setText(user.username)
        tanggalLahirProfilEditText.setText(user.birthdate)

        tanggalLahirProfilEditText.setOnClickListener {
            val dpd = DatePickerDialog(requireActivity(), android.R.style.Theme_Material_Dialog,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                val mMonth1 = mMonth + 1
                tanggalLahirProfilEditText.setText("" + mYear + "/" + mMonth1 + "/" + mDay)
            }, year, month, day)
            dpd.show()
        }

        editProfilButton.setOnClickListener {
            updateUser()
        }

        logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun updateUser() {
        val jobj = JSONObject()
        try {
            jobj.put("fullName", namaLengkapProfilEditText.text)
            jobj.put("username", usernameEditText.text)
            jobj.put("birthdate", tanggalLahirProfilEditText.text)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val sessionManager = SessionManager(requireActivity().applicationContext)
        val token = JSONObject(sessionManager.getToken())
        AndroidNetworking.post("https://risfund.loophole.site/api/user/update")
            .addJSONObjectBody(jobj)
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(com.androidnetworking.common.Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if(response!!.getString("message").equals("User has been updated")) {
                            val data = response.getJSONArray("data")
                            val firstData = data.getJSONObject(1)

                            val userId = firstData.getString("userId")
                            val fullName = firstData.getString("fullName")
                            val phoneNumber = firstData.getString("phoneNumber")
                            val birthdate = firstData.getString("birthdate")
                            val gender = firstData.getString("gender")
                            val username = firstData.getString("username")
                            val balance = firstData.getString("balance")
                            val userQr = firstData.getString("userQr")

                            userList.clear()

                            val responses = GetUserResponse(userId.toInt(), fullName, phoneNumber, birthdate, gender, username, balance.toInt(), userQr)

                            userList.add(responses)

                             val intent = Intent(activity!!.applicationContext, HomeActivity2::class.java)
                            intent.putExtra("dataUser", responses)
                            startActivity(intent)

//                            val fragment = SettingFragment()
//                            activity!!.supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                        }
                    } catch (e: JSONException) {
                        Log.d("eResponse", e.toString())
                    }
                }

                override fun onError(anError: ANError?) {
                    if(anError!!.errorCode != 0) {
                        val response = JSONObject(anError.errorBody)
                        if(response.getString("message") != "User has been updated") {
                            Toast.makeText(requireActivity().applicationContext, response.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })

    }


    private fun logout() {
        val sessionManager = SessionManager(requireActivity().applicationContext)
        val token = JSONObject(sessionManager.getToken())
        AndroidNetworking.post("https://risfund.loophole.site/api/logout")
            .addHeaders("Accept", "application/json")
            .addHeaders("Authorization", "Bearer " + token.getString("token"))
            .setPriority(com.androidnetworking.common.Priority.MEDIUM)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    try {
                        if (response != null) {
                            if(response.getString("message").equals("success")) {
                                val logout = sessionManager.clearToken()

                                val intent = Intent(activity, LoginActivity::class.java)
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
                            Toast.makeText(activity!!.applicationContext, response.getString("message"), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
    }
}