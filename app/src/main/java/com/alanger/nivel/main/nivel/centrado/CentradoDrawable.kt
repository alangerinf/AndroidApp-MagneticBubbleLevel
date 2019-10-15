package com.alanger.nivel.main.nivel.centrado

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.Bitmap
import android.os.Vibrator
import android.util.Log
import com.alanger.nivel.R


class CentradoDrawable(ctx: Context, vibratorService : Vibrator) : Drawable() {



  var isBorder = false

  private val myVibratorService = vibratorService

  /*
   * Base
   */
  private val basePaint = Paint().apply {
    color = 0xFF000000.toInt()
  }

  private val blackStrokePaint = Paint().apply {
    color = 0xFF000000.toInt()
    strokeWidth = 3F
    style = Paint.Style.STROKE
  }

  private val greenStrokePaint = Paint().apply {
    color = 0xFF00FF00.toInt()
    strokeWidth = 3F
    style = Paint.Style.STROKE
  }

  private val whiteStrokePaint = Paint().apply {
      color = 0xFF888888.toInt()
    strokeWidth = 3F
    style = Paint.Style.STROKE
  }

  var scale = 1f
    set(value) {
      field = value
      invalidateSelf()
    }

  var angleX = 0f
    set(value) {
      field = value
      invalidateSelf()
    }

  var angleY = 0f
    set(value) {
      field = value
      invalidateSelf()
    }

  private val holePath = Path()

  var icon:Bitmap ?= null

  private val context = ctx





  override fun onBoundsChange(bounds: Rect?) {
    super.onBoundsChange(bounds)

    // Update the clip path to be a circle that is 1/3 the diameter of the donut
    bounds?.let {
      holePath.reset()
      holePath.addCircle(it.exactCenterX(), it.exactCenterY(), it.width() / 6F, Path.Direction.CW)
    }
  }

  override fun draw(canvas: Canvas?) {

    val cx = bounds.exactCenterX()
    val cy = bounds.exactCenterY()

    val baseRadius = (bounds.width() / 2f)

    val pointerRadius = baseRadius/6f
    val maxTranslate_AXIS = baseRadius - pointerRadius

    icon = BitmapFactory.decodeResource(context.resources, R.drawable.ball2)
    icon = Bitmap.createScaledBitmap(
            icon, ((pointerRadius*2f)).toInt(),(pointerRadius*2f).toInt(), false)


      var  circlebase = BitmapFactory.decodeResource(context.resources, R.drawable.mycircle)
      circlebase = Bitmap.createScaledBitmap(circlebase,baseRadius.toInt()*2,baseRadius.toInt()*2,false)

        canvas?.let {
/*
          canvas.drawBitmap(
                  circlebase,
                  0f,
                  0f,
                  null
          )
*/
          canvas.drawCircle(
                  cx,
                  cy,
                  maxTranslate_AXIS*0.25f,
                  whiteStrokePaint)

          canvas.drawCircle(
                  cx,
                  cy,
                  maxTranslate_AXIS*0.5f,
                  greenStrokePaint)

          canvas.drawCircle(
                  cx,
                  cy,
                  maxTranslate_AXIS*0.75F,
                  greenStrokePaint)

          canvas.drawCircle(
                  cx,
                  cy,
                  maxTranslate_AXIS,
                  greenStrokePaint)


          canvas.drawLine(
                  cx,0f+3,
                  cx,(cy*2)-3,
                  whiteStrokePaint
          )

          canvas.drawLine(
                  0+3f,cy,
                  (cx*2)-3,cy,
                  whiteStrokePaint
          )

/*
          //borde externo
          canvas.drawCircle(
                  cx,
                  cy,
                  baseRadius-3,
                  blackStrokePaint)

          //pequeño irulo negro medio


          canvas.drawCircle(
                  cx,
                  cy,
                  maxTranslate_AXIS*0.125f,
                  basePaint)



          canvas.drawCircle(
              cx, // cx
              cy, // cy
              baseRadius, // radius
              basePaint)


             */
        }

      val maxAngleX= converMaxAngle(angleX)
      val maxAngleY= converMaxAngle(angleY)


    var posX = maxTranslate_AXIS*(maxAngleX/90f)
    var posY = maxTranslate_AXIS*(maxAngleY/90f)


    Log.d("Donut",""+posX+" "+posY)

    val h = Math.sqrt(( Math.pow(posX.toDouble(), 2.0) ) + (Math.pow(posY.toDouble(), 2.0) ))

    if(h>maxTranslate_AXIS){
      val sinAng = posY/h
      val cosAng = posX/h
      posX = (cosAng * maxTranslate_AXIS).toFloat()
      posY = (sinAng * maxTranslate_AXIS).toFloat()

      if(!isBorder){
        myVibratorService.vibrate(100)
      }
      isBorder=true
    }else{
      isBorder= false
    }

    canvas?.translate(posX,0f)
    canvas?.translate(0f,posY)


    canvas?.save()

   // val  scalePointer = ((baseRadius/(icon!!.width))*pointerRadius)/(baseRadius/2f)


  //  canvas?.translate(cx,cy)

   // canvas?.scale(scalePointer,scalePointer)

    canvas?.let {
        canvas.drawBitmap(
                icon
                ,cx-(pointerRadius)
                ,cy-(pointerRadius)
                ,null)



    }

    canvas?.restore()

/*
    canvas?.drawCircle(
            cx,
            cy,
            pointerRadius,
            icingPaint)
*/
  }

    private fun converMaxAngle(fl: Float): Float {

        val temp = 30f


        if (fl>temp){
            return temp*(90/temp)
        }else{
            if(fl < -temp){
                return -temp*(90/temp)
            }
        }
        return fl*(90/temp)
    }

    override fun setAlpha(alpha: Int) {
    throw IllegalStateException("Who wants an invisible donut?")
  }

  override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

  override fun setColorFilter(colorFilter: ColorFilter?) {
    TODO("not implemented")
  }
}
