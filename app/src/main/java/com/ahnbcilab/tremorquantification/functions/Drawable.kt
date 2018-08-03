package com.ahnbcilab.tremorquantification.functions

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

abstract class Drawable(context: Context) : View(context) {
    protected val path = Path()
    protected val paint = Paint()
    protected val bitmapPaint = Paint(Paint.DITHER_FLAG)

    protected lateinit var bitmap: Bitmap
    protected lateinit var canvas: Canvas

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, 0.toFloat(), 0.toFloat(), bitmapPaint)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x: Float = event.x
        val y: Float = event.y

        when(event.action) {
            MotionEvent.ACTION_DOWN -> path.moveTo(x, y)
            MotionEvent.ACTION_MOVE -> path.lineTo(x, y)
        }

        invalidate()
        return true
    }

    fun clear() {
        path.reset()
        bitmap.eraseColor(Color.TRANSPARENT)
    }
}