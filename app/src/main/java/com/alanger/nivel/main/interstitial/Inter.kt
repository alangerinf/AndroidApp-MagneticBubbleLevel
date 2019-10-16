package com.alanger.nivel.main.interstitial

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.alanger.nivel.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd


class Inter (ctx: Context){

    companion object{
        var count = 0
        private var mInterstitialAd: InterstitialAd? = null
    }

    private val context : Context = ctx

    init {
        mInterstitialAd = newInterstitialAd()
        loadInterstitial()
    }

    fun init(){
        Log.d("interstitial","init()")
    }
    fun tryShow(){

        Log.d("interstitial","tryShow()")
        if(count%2==0){
            Log.d("interstitial","count=0")
            count=0
            showInterstitial()
        }
        count += 1
    }

    private fun newInterstitialAd(): InterstitialAd {
        val interstitialAd = InterstitialAd(context)
        interstitialAd.adUnitId = context.getString(R.string.interstitial_ad_unit_id)
        interstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                //    Toast.makeText(applicationContext, "Ad Loaded", Toast.LENGTH_SHORT).show()
                Log.d("interstitial","onAdLoaded()")
            }

            override fun onAdFailedToLoad(errorCode: Int) {
            //       Toast.makeText(context, "Ad Failed To Load $errorCode",Toast.LENGTH_SHORT).show()
                Log.d("interstitial","errorCode:$errorCode")
            }

            override fun onAdClosed() {
                // Proceed to the next level.
                // goToNextLevel()
                //Toast.makeText(applicationContext, "Ad Closed", Toast.LENGTH_SHORT).show()
                Log.d("interstitial","onAdClosed()")
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
            tryToLoadAdOnceAgain()
        }
    }

    private fun tryToLoadAdOnceAgain() {
        mInterstitialAd = newInterstitialAd()
        loadInterstitial()
    }


}

