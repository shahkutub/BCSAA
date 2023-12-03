package com.bcsaa

import android.support.v7.app.AppCompatActivity
import android.os.Bundle;
import android.util.Log
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.bcsaa.utils.AppConstant

public class CourseContentView  : AppCompatActivity(){

    // Declaring a variable of type WebView
    private lateinit var pdfView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.course_content_view)

        // Initialize the WebView by finding it in the layout
        pdfView = findViewById(R.id.webViewCourse)

        // URL of the PDF you want to display(Make Sure to add Preview)
        val pdfUrl = AppConstant.pdfurl
        Log.e("pdf",AppConstant.pdfurl);

        // Setting up the WebView with the PDF URL
        setupWebViewWithUrl(pdfView, pdfUrl)
    }

    // This function configures the WebView to display the PDF.
    private fun setupWebViewWithUrl(webView: WebView?, url: String) {
        webView?.let {
            // Enable JavaScript in the WebView
            it.settings.javaScriptEnabled = true
            it.settings.loadWithOverviewMode = true
            it.settings.useWideViewPort = true

            // Configure a WebViewClient to handle navigation events
            it.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    // Return false to allow the WebView to handle the URL
                    return false
                }
            }

            // Configure a WebChromeClient (optional)
            it.webChromeClient = object : WebChromeClient() {}

            // Generate HTML content to embed the PDF
            val htmlContent = getPDFHtml(url)

            // Load the HTML content into the WebView
            it.loadData(htmlContent, "text/html", "utf-8")
        }
    }

    // This function generates the HTML content to embed the PDF.
    private fun getPDFHtml(url: String): String {
        return """ 
            <!DOCTYPE html> 
            <html> 
            <head> 
                <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"> 
                <style> 
                    body, html { 
                        margin: 0; 
                        height: 100%; 
                        overflow: hidden; 
                    } 
                    iframe { 
                        position: absolute; 
                        top: 0; 
                        left: 0; 
                        width: 100%; 
                        height: 100%; 
                        border: none; 
                    } 
                </style> 
            </head> 
            <body> 
                <iframe src="$url" allow="autoplay"></iframe> 
            </body> 
            </html> 
        """
    }
}