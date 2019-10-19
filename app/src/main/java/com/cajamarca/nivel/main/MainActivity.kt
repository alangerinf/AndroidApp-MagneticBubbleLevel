package com.cajamarca.nivel.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cajamarca.nivel.R
import com.cajamarca.nivel.main.interstitial.Inter
import com.cajamarca.nivel.main.nivel.NivelEmpezarAhoraActivity
import com.cajamarca.nivel.main.qr.QREmpezarAhoraActivity
import com.google.android.gms.ads.*
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {




    private val adViewTop: AdView by lazy { findViewById<AdView>(R.id.adViewTop) }
    private val adViewBottom: AdView by lazy { findViewById<AdView>(R.id.adViewBottom) }


    private val btnNivel: MaterialButton by lazy { findViewById<MaterialButton>(R.id.btnNivel) }
    private val btnQR: MaterialButton by lazy { findViewById<MaterialButton>(R.id.btnQR) }


   // private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)

        val adRequestTop = AdRequest.Builder().build()
        val adRequestBottom = AdRequest.Builder().build()
        adViewTop.loadAd(adRequestTop)
        adViewBottom.loadAd(adRequestBottom)

        val inter = Inter(this)
        inter.init()


//        mInterstitialAd = newInterstitialAd()
//        loadInterstitial()


        btnNivel.setOnClickListener {
            val i = Intent(this, NivelEmpezarAhoraActivity::class.java)
            startActivity(i)
            inter.tryShow()
        }

        btnQR.setOnClickListener {
            val i = Intent(this, QREmpezarAhoraActivity::class.java)
            startActivity(i)
            inter.tryShow()
        }
    }
/*
    private fun newInterstitialAd(): InterstitialAd {
        val interstitialAd = InterstitialAd(this)
        interstitialAd.adUnitId = getString(R.string.interstitial_ad_unit_id)
        interstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
            //    Toast.makeText(applicationContext, "Ad Loaded", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
             //   Toast.makeText(applicationContext, "Ad Failed To Load", Toast.LENGTH_SHORT).show()
            }

            override fun onAdClosed() {
                // Proceed to the next level.
                // goToNextLevel()
                //Toast.makeText(applicationContext, "Ad Closed", Toast.LENGTH_SHORT).show()
                tryToLoadAdOnceAgain()
            }
        }
        return interstitialAd
    }

    private fun loadInterstitial() {
        // Disable the load ad button and load the ad.
        val adRequest = AdRequest.Builder().build()
        mInterstitialAd!!.loadAd(adRequest)
    }

    private fun showInterstitial() {
        // Show the ad if it is ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd!!.isLoaded) {
            mInterstitialAd!!.show()
        } else {
           // Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show()
            tryToLoadAdOnceAgain()
        }
    }

    private fun tryToLoadAdOnceAgain() {
        mInterstitialAd = newInterstitialAd()
        loadInterstitial()
    }
*/

}
