package com.alanger.nivel

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*


class MainActivity : AppCompatActivity() , SensorEventListener  {


  private var mSensorManager: SensorManager? = null
  private var mSensorAcc: Sensor? = null



  private val platter: ImageView by lazy { findViewById<ImageView>(R.id.platter) }
  private val scale: SeekBar by lazy { findViewById<SeekBar>(R.id.seekBar) }

  private val donut = DonutDrawable(this)


  private val tViewX: TextView by lazy { findViewById<TextView>(R.id.tViewX) }
  private val tViewY: TextView by lazy { findViewById<TextView>(R.id.tViewY) }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_metal_ball)

    // Get the sensors to use
    mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    mSensorAcc = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


    platter.setImageDrawable(donut)


    // setup the window
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

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



  //  setSupportActionBar(toolbar)

    scale.progress = (scale.max * donut.scale).toInt()
    scale.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
      override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (!fromUser) return
        donut.scale = progress.toFloat() / scale.max
      }
      override fun onStartTrackingTouch(seekBar: SeekBar?) {}
      override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })

  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

  }

  override fun onSensorChanged(event: SensorEvent?) {

    Log.d("hello","onSensorChanged()")

    /*
    if (event?.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {

      if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
        tViewX.setText("--")
        tViewY.setText("--")
        tViewZ.setText("--")

      return
    }
*/


    var g0 = event!!.values[0].toDouble()
    var q1 = event!!.values[1].toDouble()
    var q2 = event!!.values[2].toDouble()



    val norm_Of_g = Math.sqrt((g0 * g0 + q1 * q1 +q2 * q2))

    g0 = g0 / norm_Of_g
    q1 = q1 / norm_Of_g
    q2 = q2 / norm_Of_g


/*
    val inclinationx = Math.round(Math.toDegrees(Math.acos(g[0].toDouble()))).toInt()
    val inclinationy = Math.round(Math.toDegrees(Math.acos(g[1].toDouble()))).toInt()
    val inclinationz = Math.round(Math.toDegrees(Math.acos(g[2].toDouble()))).toInt()
*/

//    val rotationx =  Math.round(Math.toDegrees(Math.atan2(g0, q1)))// angulo de lado

//    val yOrientation = event!!.values[1] / (Math.abs(  event!!.values[1]))

   // val rotationy =  Math.round( (Math.toDegrees(Math.acos(q2.toDouble())))  * yOrientation)

 //   val rotationz =  Math.round(Math.toDegrees(Math.acos(g[2].toDouble()))) //angulo parado

      if (event != null) {

        val x = ("%.1f".format(g0*90))
        val y = ("%.1f".format(q1*90))
//        val z = ("%.2f".format(rotationz))

        tViewX.text = ""+x
        tViewY.text = ""+y




      //  Toast.makeText(this,"x"+x,Toast.LENGTH_SHORT).show()

      }
      /*

      if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
        tViewX!!.text = "x = " + java.lang.Float.toString(event.values[0])
        tViewY!!.text = "y = " + java.lang.Float.toString(event.values[1])
        tViewZ!!.text = "z = " + java.lang.Float.toString(event.values[2])
       // detectShake(event)
      } else if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
        tViewX!!.text = "x = " + java.lang.Float.toString(event.values[0])
        tViewY!!.text = "y = " + java.lang.Float.toString(event.values[1])
        tViewZ!!.text = "z = " + java.lang.Float.toString(event.values[2])
       // detectRotation(event)
      }
*/


/*
      if (event != null) {

      val x = ("%.2f".format(event.values[0]))
      val y = ("%.2f".format(event.values[1]))
      val z = ("%.2f".format(event.values[2]))

      tViewX.setText( ""+x)
      tViewY.text = ""+y
      tViewZ.text = ""+z

      Toast.makeText(this,"x"+x,Toast.LENGTH_SHORT).show()
*/
//      ground!!.updateMe(event.values[1] , event.values[0])


  }
  override fun onResume() {
    super.onResume()
    Log.d("hello","OnResume")
    mSensorManager!!.registerListener(this, mSensorAcc, SensorManager.SENSOR_DELAY_NORMAL)
  }

    override fun onPause() {
        super.onPause()

      Log.d("hello","OnPause")
        mSensorManager!!.unregisterListener(this)
    }


}
