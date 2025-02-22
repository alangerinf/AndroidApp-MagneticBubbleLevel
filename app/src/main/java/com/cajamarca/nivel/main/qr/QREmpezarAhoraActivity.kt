package com.cajamarca.nivel.main.qr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cajamarca.nivel.R
import com.cajamarca.nivel.main.interstitial.Inter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class QREmpezarAhoraActivity : AppCompatActivity() {



    private val btnEmpezar: Button by lazy { findViewById<Button>(R.id.btnEmpezar) }

    private val adViewTop: AdView by lazy { findViewById<AdView>(R.id.adViewTop) }
    private val adViewBottom: AdView by lazy { findViewById<AdView>(R.id.adViewBottom) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empezar_ahora_qr)

        title = "Herramienta QR"

        MobileAds.initialize(this)

        val adRequestTop = AdRequest.Builder().build()
        val adRequestBottom = AdRequest.Builder().build()
        adViewTop.loadAd(adRequestTop)
        adViewBottom.loadAd(adRequestBottom)
        val inter = Inter(this)
        inter.init()

        btnEmpezar.setOnClickListener {
            val i = Intent(this, QRSelectActivity::class.java)
            startActivity(i)

            inter.tryShow()
        }

    }
}
