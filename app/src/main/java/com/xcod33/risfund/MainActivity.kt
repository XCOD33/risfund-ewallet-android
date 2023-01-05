package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set a delay for the splash screen
        val splashDelay: Long = 3000 // 3 seconds

        Handler().postDelayed({
            // Launch the main activity
<<<<<<< HEAD
            startActivity(Intent(this, HomeActivity::class.java))
=======
            startActivity(Intent(this, PlnPascabayarActivity::class.java))
>>>>>>> 4cd7e451affd6dc960fbdb4feda1dd6b60c9c6d6
            // Close the splash screen activity
            finish()
        }, splashDelay)
    }
}
