package com.alanger.nivel.main.nivel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.alanger.nivel.R
import com.alanger.nivel.main.interstitial.Inter
import com.alanger.nivel.main.nivel.centrado.NivelCentradoActivity
import com.alanger.nivel.main.nivel.echado.NivelEchadoActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class NivelSelectActivity : AppCompatActivity() {

    private val adViewTop: AdView by lazy { findViewById<AdView>(R.id.adViewTop) }
    private val adViewBottom: AdView by lazy { findViewById<AdView>(R.id.adViewBottom) }

    private val btnCentrado: Button by lazy { findViewById<Button>(R.id.btnCentrado) }
    private val btnEchado: Button by lazy { findViewById<Button>(R.id.btnEchado) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nivel_select)

        MobileAds.initialize(this)

        val adRequestTop = AdRequest.Builder().build()
        val adRequestBottom = AdRequest.Builder().build()
        adViewTop.loadAd(adRequestTop)
        adViewBottom.loadAd(adRequestBottom)

        val inter = Inter(this)
        inter.init()

        btnCentrado.setOnClickListener {
            val i = Intent(this, NivelCentradoActivity::class.java)
            startActivity(i)
            inter.tryShow()
        }

        btnEchado.setOnClickListener {
            val i = Intent(this, NivelEchadoActivity::class.java)
            startActivity(i)
            inter.tryShow()
        }


    }
}
