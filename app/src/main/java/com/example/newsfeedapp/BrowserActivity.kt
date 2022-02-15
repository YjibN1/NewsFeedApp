package com.example.newsfeedapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class BrowserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        val url = intent.getStringExtra("url") ?: ""

        val browser: WebView = findViewById(R.id.webBrowser)
        browser.loadUrl(url)
    }
}