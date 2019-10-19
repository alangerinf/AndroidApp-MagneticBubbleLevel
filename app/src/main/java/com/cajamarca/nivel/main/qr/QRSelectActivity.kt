package com.cajamarca.nivel.main.qr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.cajamarca.nivel.R
import com.cajamarca.nivel.main.interstitial.Inter
import com.cajamarca.nivel.main.qr.crear.CrearQRActivity
import com.cajamarca.nivel.main.qr.leer.CustomScannerActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class QRSelectActivity : AppCompatActivity() {



    private val btnLeerQR: Button by lazy { findViewById<Button>(R.id.btnLeerQR) }
    private val btnCrearQR: Button by lazy { findViewById<Button>(R.id.btnCrearQR) }


    private val adViewTop: AdView by lazy { findViewById<AdView>(R.id.adViewTop) }
    private val adViewBottom: AdView by lazy { findViewById<AdView>(R.id.adViewBottom) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrselect)

        title = "Herramienta QR"

        MobileAds.initialize(this)

        val adRequestTop = AdRequest.Builder().build()
        val adRequestBottom = AdRequest.Builder().build()
        adViewTop.loadAd(adRequestTop)
        adViewBottom.loadAd(adRequestBottom)

        val inter = Inter(this)
        inter.init()

        btnLeerQR.setOnClickListener {
            val i = Intent(this, CustomScannerActivity::class.java)
            startActivity(i)

            inter.tryShow()
        }

        btnCrearQR.setOnClickListener {
            val i = Intent(this, CrearQRActivity::class.java)
            startActivity(i)

            inter.tryShow()
        }



    }
}
