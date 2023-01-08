package com.xcod33.risfund

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import com.xcod33.risfund.data.GetUserResponse

class TripayWebViewActivity : AppCompatActivity() {

    private lateinit var closeTripayWebView: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tripay_web_view)

        val user = intent.getParcelableExtra<GetUserResponse>("dataUser")

        closeTripayWebView = findViewById(R.id.closeTripayWebView)
        val webView: WebView = findViewById(R.id.webview)

// WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl(intent.getStringExtra("checkout_url").toString())

        // this will enable the javascript settings, it can also allow xss vulnerabilities
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)

        closeTripayWebView.setOnClickListener {
            val intent = Intent(this, TopUpActivity::class.java)
            intent.putExtra("dataUser", user)
            startActivity(intent)
        }
    }

    // if you press Back button this code will work
    override fun onBackPressed() {

        val webView: WebView = findViewById(R.id.webview)

        // if your webview can go back it will go back
        if (webView.canGoBack())
            webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}