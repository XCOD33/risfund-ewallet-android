package com.xcod33.risfund

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xcod33.risfund.data.GetUserResponse
import com.xcod33.risfund.databinding.ActivityHome2Binding

class HomeActivity2 : AppCompatActivity() {
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

    private fun  replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}