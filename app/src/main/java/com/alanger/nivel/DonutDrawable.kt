package com.alanger.nivel

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.annotation.Keep
import android.widget.Toast
import java.security.AccessController.getContext
import java.util.*
import android.graphics.Bitmap



class DonutDrawable(ctx: Context) : Drawable() {

  /*
   * Base
   */
  private val basePaint = Paint().apply {
    color = 0xFFFFFFFF.toInt()
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

    val pointerRadius = baseRadius/5f
    val maxTranslate_AXIS = baseRadius - pointerRadius

    icon = BitmapFactory.decodeResource(context.resources,R.drawable.ball)
    icon = Bitmap.createScaledBitmap(
            icon, ((baseRadius/13f)+(pointerRadius*2f)).toInt(),( +(baseRadius/13f)+(pointerRadius*2f)).toInt(), false)

    canvas?.let {

      canvas.scale(scale, scale,
          bounds.width() / 2f,
          bounds.height() / 2f)

      canvas.drawCircle(
          cx, // cx
          cy, // cy
          baseRadius, // radius
          basePaint)
    }


    canvas?.translate(maxTranslate_AXIS,0f)

   // canvas?.translate(0f,maxTranslate_AXIS)


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

  override fun setAlpha(alpha: Int) {
    throw IllegalStateException("Who wants an invisible donut?")
  }

  override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

  override fun setColorFilter(colorFilter: ColorFilter?) {
    TODO("not implemented")
  }
}
