package com.alanger.nivel.main.qr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alanger.nivel.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class QRSelectActivity : AppCompatActivity() {



    private val adViewTop: AdView by lazy { findViewById<AdView>(R.id.adViewTop) }
    private val adViewBottom: AdView by lazy { findViewById<AdView>(R.id.adViewBottom) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrselect)

        MobileAds.initialize(this)

        val adRequestTop = AdRequest.Builder().build()
        val adRequestBottom = AdRequest.Builder().build()
        adViewTop.loadAd(adRequestTop)
        adViewBottom.loadAd(adRequestBottom)


    }
}
