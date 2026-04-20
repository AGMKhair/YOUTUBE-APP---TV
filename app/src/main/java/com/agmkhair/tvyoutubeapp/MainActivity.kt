package com.agmkhair.tvyoutubeapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.agmkhair.tvyoutubeapp.ui.theme.YOUTUBEAPPTVTheme

class MainActivity : ComponentActivity() {

    private var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ব্যাক বাটন হ্যান্ডেল করা হয়েছে যাতে ইউটিউবের আগের পেজে যাওয়া যায়
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView?.canGoBack() == true) {
                    webView?.goBack()
                } else {
                    finish()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)

        setContent {
            YOUTUBEAPPTVTheme {
                YouTubeWebView()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun YouTubeWebView() {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    this@MainActivity.webView = this

                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        mediaPlaybackRequiresUserGesture = false
                        // YouTube TV ইন্টারফেস নিশ্চিত করার জন্য User Agent
                        userAgentString = "Mozilla/5.0 (Linux; Tizen 2.3) AppleWebKit/538.1 (KHTML, like Gecko) Version/2.3 TV Safari/538.1"
                        loadWithOverviewMode = true
                        useWideViewPort = true
                    }

                    webViewClient = WebViewClient()
                    loadUrl("https://www.youtube.com/tv")
                    
                    // টিভি রিমোট ফোকাস করার জন্য
                    isFocusable = true
                    isFocusableInTouchMode = true
                    requestFocus()
                }
            }
        )
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // রিমোটের অন্যান্য কি (Key) হ্যান্ডেল করার প্রয়োজন হলে এখানে করা যাবে
        return super.onKeyDown(keyCode, event)
    }
}
