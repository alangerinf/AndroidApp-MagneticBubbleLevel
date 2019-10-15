package com.alanger.nivel.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alanger.nivel.main.nivel.NivelSelectActivity
import com.alanger.nivel.R
import com.alanger.nivel.main.qr.QRSelectActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {



    private val adViewTop: AdView by lazy { findViewById<AdView>(R.id.adViewTop) }
    private val adViewBottom: AdView by lazy { findViewById<AdView>(R.id.adViewBottom) }


    private val btnNivel: MaterialButton by lazy { findViewById<MaterialButton>(R.id.btnNivel) }
    private val btnQR: MaterialButton by lazy { findViewById<MaterialButton>(R.id.btnQR) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)

        val adRequestTop = AdRequest.Builder().build()
        val adRequestBottom = AdRequest.Builder().build()
        adViewTop.loadAd(adRequestTop)
        adViewBottom.loadAd(adRequestBottom)



        btnNivel.setOnClickListener {
            val i = Intent(this, NivelSelectActivity::class.java)
            startActivity(i)
        }

        btnQR.setOnClickListener {
            val i = Intent(this, QRSelectActivity::class.java)
            startActivity(i)
        }
    }

}
