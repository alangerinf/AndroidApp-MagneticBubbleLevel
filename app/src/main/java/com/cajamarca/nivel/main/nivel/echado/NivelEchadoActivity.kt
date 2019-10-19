package com.cajamarca.nivel.main.nivel.echado

import android.app.Service
import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import android.view.OrientationEventListener
import com.cajamarca.nivel.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class NivelEchadoActivity : AppCompatActivity() , SensorEventListener  {


  private var mSensorManager: SensorManager? = null
  private var mSensorAcc: Sensor? = null

  private val platter: ImageView by lazy { findViewById<ImageView>(R.id.platter) }

  private var centrado : EchadoDrawable? = null

  private val adViewBottom: AdView by lazy { findViewById<AdView>(R.id.adViewBottom) }

  private val tViewX: TextView by lazy { findViewById<TextView>(R.id.tViewX) }


  private val fabReset: FloatingActionButton by lazy { findViewById<FloatingActionButton>(R.id.floatingActionButton) }



  var vibratorService : Vibrator?= null



  var  G0 : Double? = 0.0
  var  G1 : Double? = 0.0
  var  G2 : Double? = 0.0

  var  resetX : Double? = 0.0
  var  resetY : Double? = 0.0



  var orientacion = 1


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_nivel_echado)


    MobileAds.initialize(this)
    val adRequestBottom = AdRequest.Builder().build()
    adViewBottom.loadAd(adRequestBottom)


    vibratorService = (baseContext.getSystemService(Service.VIBRATOR_SERVICE)) as Vibrator

    // Get the sensors to use
    mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    mSensorAcc = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    centrado = EchadoDrawable(this, vibratorService!!)

    platter.setImageDrawable(centrado)


    // setup the window
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
      window.decorView.systemUiVisibility =   View.SYSTEM_UI_FLAG_LAYOUT_STABLE
      View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
      View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
      View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
      View.SYSTEM_UI_FLAG_FULLSCREEN
      View.SYSTEM_UI_FLAG_IMMERSIVE
    }

    fabReset.setOnClickListener {
      resetX = G0
      resetY = G1
    }


    var mOrientationEventListener = object : OrientationEventListener(this, SensorManager.SENSOR_DELAY_GAME) {

      override fun onOrientationChanged(orientation: Int) {

        if (orientation == 0) {
          Log.e("TEST", "orientation-Portrait  = $orientation")
        } else if (orientation == 90) {
          orientacion=-1
          Log.e("TEST", "orientation-Landscape = $orientation")
        } else if (orientation == 180) {
          Log.e("TEST", "orientation-Portrait-rev = $orientation")
        } else if (orientation == 270) {
          orientacion=1
          Log.e("TEST", "orientation-Landscape-rev = $orientation")
        } else if (orientation == 360) {
          Log.e("TEST", "orientation-Portrait= $orientation")
        }

      }
    }

    mOrientationEventListener.enable()


  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

  }

  override fun onSensorChanged(event: SensorEvent?) {

    Log.d("hello","onSensorChanged()")

    var g0 = event!!.values[0].toDouble()
    var g1 = event!!.values[1].toDouble()
    var g2 = event!!.values[2].toDouble()
    
    Log.d("helloo","["+g0+"\t"+g1+"\t"+g2)
    
    
    val norm_Of_g = Math.sqrt((g0 * g0 + g1 * g1 +g2 * g2))

    g0 = g0 / norm_Of_g
    g1 = g1 / norm_Of_g
   // q2 = q2 / norm_Of_g


    var resitencia = 10

    G0 =   (((G0!!*resitencia) + g0 ) /(resitencia+1))
    G1 =   (((G1!!*resitencia) + g1 ) /(resitencia+1))

     val inclinationz = Math.round(Math.toDegrees(Math.acos(g2))).toInt()


//    val rotationx =  Math.round(Math.toDegrees(Math.atan2(g0, q1)))// angulo de lado

//    val yOrientation = event!!.values[1] / (Math.abs(  event!!.values[1]))

   // val rotationy =  Math.round( (Math.toDegrees(Math.acos(q2.toDouble())))  * yOrientation)

 //   val rotationz =  Math.round(Math.toDegrees(Math.acos(g[2].toDouble()))) //angulo parado

      if (event != null) {

        var x_ = (G0!!-resetX!!) *90
        var y_ = -((G1!!-resetY!!) *90) *orientacion
        /*
        if(requestedOrientation == Configuration.ORIENTATION_){
          x_ = -x_
          y_ = -y_
        }else{


        }
         */
        val x = "%.1f".format(x_)
        val y = "%.1f".format(y_)

//        val z = ("%.2f".format(rotationz))

        try{
          centrado!!.angleX = x.toFloat()
          centrado!!.angleY = y.toFloat()
        }catch (e: Exception){
          centrado!!.angleX = x.replace(",",".").toFloat()
          centrado!!.angleY = y.replace(",",".").toFloat()
        }finally {
          tViewX.text = ""+(centrado!!.angleY)

        }

      //  Toast.makeText(this,"x"+x,Toast.LENGTH_SHORT).show()

      }

  }
  override fun onResume() {
    super.onResume()
    Log.d("hello","OnResume")
    mSensorManager!!.registerListener(this, mSensorAcc, SensorManager.SENSOR_DELAY_GAME)
  }

  override fun onPause() {
      super.onPause()

    Log.d("hello","OnPause")
      mSensorManager!!.unregisterListener(this)
  }


}
