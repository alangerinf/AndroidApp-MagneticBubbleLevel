package com.cajamarca.nivel.main.qr.leer

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.cajamarca.nivel.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory

import java.util.ArrayList
import java.util.Arrays
import java.util.Random

import android.Manifest.permission.CAMERA
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

/**
 * Custom Scannner Activity extending from Activity to display a custom layout form scanner view.
 */
class CustomScannerActivity : AppCompatActivity(), DecoratedBarcodeView.TorchListener {

    private var barcodeScannerView: DecoratedBarcodeView? = null

    private val TAG = "QRScaner"

    private val capture: CaptureManager? = null


    private var beepManager: BeepManager? = null
    private val callback = object : BarcodeCallback {

        internal var handler = Handler()

        internal var runnable: Runnable = Runnable {
            beepManager!!.isVibrateEnabled = true
            beepManager!!.playBeepSoundAndVibrate()
        }

        override fun barcodeResult(result: BarcodeResult) {

            val resultado = result.text.toString()
            Log.d(TAG, "tamaño " + resultado.length)


            if (resultado == null/* || verifiarDuplicado(resultado)*/) {//si s e rechazo
                // Prevent duplicate scans
                Log.d(TAG, resultado + "DUPLICADO")
                return
            } else {
                //  listDNI.add(resultado);
                Log.d(TAG, resultado + "ingresado")

                barcodeScannerView!!.setStatusText(result.text)
                handler.post(runnable)
                /*
                Intent returnIntent = new Intent();
                returnIntent.putExtra("qr",resultado);
                setResult(RESULT_OK,returnIntent);
                finish();

                 */
                showPopupRecomendacion(resultado)
            }
        }


        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    private val adViewBottom: AdView by lazy { findViewById<AdView>(R.id.adViewBottom) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_scanner)
        title = "Lector QR"


        MobileAds.initialize(this)
        val adRequestBottom = AdRequest.Builder().build()
        adViewBottom.loadAd(adRequestBottom)

        statusLight = false
        fAButtonLinterna = findViewById(R.id.fAButtonLinterna)

        val b = intent.extras



        listDNI = ArrayList()
        validarPermisos()

        fAButtonLinterna!!.setOnClickListener {
            if (statusLight) {
                statusLight = false
                //   Toast.makeText(getBaseContext(),"apagando",Toast.LENGTH_SHORT).show();
                fAButtonLinterna!!.setImageResource(R.drawable.ic_light_white_off)
                barcodeScannerView!!.setTorchOff()

            } else {
                statusLight = true
                // Toast.makeText(getBaseContext(), "encendiendo", Toast.LENGTH_SHORT).show();
                fAButtonLinterna!!.setImageResource(R.drawable.ic_highlight_white_on)
                barcodeScannerView!!.setTorchOn()
            }
        }


        barcodeScannerView = findViewById<View>(R.id.zxing_barcode_scanner) as DecoratedBarcodeView
        barcodeScannerView!!.setTorchListener(this)


        val formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39, BarcodeFormat.CODABAR)
        barcodeScannerView!!.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        barcodeScannerView!!.initializeFromIntent(intent)
        barcodeScannerView!!.decodeContinuous(callback)



        beepManager = BeepManager(this)
    }

    override fun onResume() {
        super.onResume()

        barcodeScannerView!!.resume()
    }

    override fun onPause() {
        super.onPause()

        barcodeScannerView!!.pause()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return barcodeScannerView!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    /**
     * Check if the device's camera has a Flashlight.
     * @return true if there is Flashlight, otherwise false.
     */
    private fun hasFlash(): Boolean {
        return applicationContext.packageManager
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    fun switchFlashlight(view: View) {

    }

    fun changeMaskColor(view: View) {
        val rnd = Random()
        val color = Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        //viewfinderView.setMaskColor(color);
    }

    override fun onTorchOn() {
        // switchFlashlightButton.setText("Apagar Linterna");
    }

    override fun onTorchOff() {
        // switchFlashlightButton.setText("Encender Linterna");
    }

    override fun onSupportNavigateUp(): Boolean {
        val returnIntent = Intent()
        setResult(Activity.RESULT_CANCELED, returnIntent)
        finish()
        onBackPressed()
        return false
    }

    internal fun verifiarDuplicado(newDNI: String): Boolean {
        for (dni in listDNI!!) {
            if (newDNI == dni) {
                return true
            }
        }
        return false
    }

    override fun onBackPressed() {


        super.onBackPressed()
        // overridePendingTransition(R.anim.right_in, R.anim.right_out);
        val returnIntent = Intent()
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    private fun validarPermisos(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        if (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true
        }

        if (shouldShowRequestPermissionRationale(CAMERA)) {
            cargarDialogoRecomendacion()
        } else {
            requestPermissions(arrayOf(CAMERA), 100)
        }
        return false

    }

    private fun cargarDialogoRecomendacion() {
        val dialog = AlertDialog.Builder(this@CustomScannerActivity)
        dialog.setTitle("Permisos Desactivados")
        dialog.setMessage("Debe aceptar todos los permisos para poder tomar fotos")
        dialog.setPositiveButton("Aceptar") { dialog, which -> solicitarPermisosManual() }
        dialog.show()
    }

    private fun solicitarPermisosManual() {
        val opciones = arrayOf<CharSequence>("si", "no")
        val alertOpciones = AlertDialog.Builder(this@CustomScannerActivity)
        alertOpciones.setTitle("¿Desea configurar los permisos de Forma Manual?")
        alertOpciones.setItems(
                opciones
        ) { dialog, i ->
            if (opciones[i] == "si") {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Los permisos no fueron aceptados", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }
        alertOpciones.show()
    }


    private fun showPopupRecomendacion(mensaje: String) {
        barcodeScannerView!!.pause()
        val dialogClose: Dialog
        dialogClose = Dialog(this)
        dialogClose.setContentView(R.layout.dialog_result_qr)
        dialogClose.setCancelable(false)
        val btnDialogClose = dialogClose.findViewById<View>(R.id.btnOk) as Button
        val eTextQRMensaje = dialogClose.findViewById<TextView>(R.id.eTextQRMensaje)
        eTextQRMensaje.text = mensaje

        eTextQRMensaje.movementMethod = LinkMovementMethod.getInstance()

        btnDialogClose.setOnClickListener {
            barcodeScannerView!!.resume()
            dialogClose.dismiss()
            // finish();
        }

        dialogClose.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogClose.show()
    }

    companion object {

        private var fAButtonLinterna: FloatingActionButton? = null

        private var statusLight: Boolean = false
        private var listDNI: List<String>? = null
    }

}
