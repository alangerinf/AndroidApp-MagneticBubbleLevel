package com.alanger.nivel.main.qr.crear

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

import com.alanger.nivel.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException

import java.util.Hashtable
import android.graphics.Color.BLACK
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class CrearQRActivity : AppCompatActivity() {




    private val adViewBottom: AdView by lazy { findViewById<AdView>(R.id.adViewBottom) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_qr)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        title = "CREAR QR"

        MobileAds.initialize(this)
        val adRequestBottom = AdRequest.Builder().build()
        adViewBottom.loadAd(adRequestBottom)

        val btnQR = findViewById<Button>(R.id.btnQR)
        val iViewQR = findViewById<ImageView>(R.id.iViewQR)
        val eTextQR = findViewById<EditText>(R.id.eTextQR)

        btnQR.setOnClickListener {
            try {
                iViewQR.setImageBitmap(createQRCode(eTextQR.text.toString(), 2018))
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }

    }

    companion object {


        private val TAG = CrearQRActivity::class.java.simpleName

        @Throws(WriterException::class)
        fun createQRCode(str: String, widthAndHeight: Int): Bitmap {
            val hints = Hashtable<EncodeHintType, String>()
            hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
            val matrix = MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight)
            val width = matrix.width
            val height = matrix.height
            val pixels = IntArray(width * height)

            for (y in 0 until height) {
                for (x in 0 until width) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = BLACK
                    }
                }
            }
            val bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            return bitmap

        }
    }
}
