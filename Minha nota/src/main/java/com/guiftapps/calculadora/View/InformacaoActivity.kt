package com.guiftapps.calculadora.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.guiftapps.calculadora.R

class InformacaoActivity : AppCompatActivity() {
    private var ads: AdView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacao)

        MobileAds.initialize(this, "ca-app-pub-2865932856120238~9749752037")
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = getString(R.string.adsCode)

        ads = findViewById(R.id.adView4)
        val adRequest = AdRequest.Builder().build()
        ads!!.loadAd(adRequest)
        val link = findViewById<TextView>(R.id.textView10)
        link.text = Html.fromHtml(getString(R.string.sobre) + "Leia a <a href=\"https://guilhermeft.wixsite.com/minhanota/politica-de-privacidade\">política de privacidade</a> do aplicativo. ")
        link.movementMethod = android.text.method.LinkMovementMethod.getInstance()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
