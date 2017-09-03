package com.vinaysshenoy.devfest17sample.widget.shapeimageview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.vinaysshenoy.devfest17sample.R

/**
 * Created by vinaysshenoy on 03/09/17.
 */
class ShapeImageView : ImageView {

    private val drawRect = RectF()
    private val bitmapRect = RectF()

    private val shaderMatrix = Matrix()

    private lateinit var bitmapShader: BitmapShader
    private lateinit var drawPaint: Paint
    private lateinit var bitmap: Bitmap

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        if (!isInEditMode) {
            bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avatar)
            bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            bitmapRect.set(0F, 0F, bitmap.width.toFloat(), bitmap.height.toFloat())
        }

        drawPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        drawPaint.style = Paint.Style.FILL
        drawPaint.shader = bitmapShader

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        if (w > 0 && h > 0) {
            drawRect.set(0F, 0F, w.toFloat(), h.toFloat())
            drawRect.left += paddingStart
            drawRect.right -= paddingEnd
            drawRect.top += paddingTop
            drawRect.bottom -= paddingBottom

            shaderMatrix.setRectToRect(bitmapRect, drawRect, Matrix.ScaleToFit.CENTER)
            bitmapShader.setLocalMatrix(shaderMatrix)

        }
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (drawRect.width() > 0F && drawRect.height() > 0F) {
            canvas!!.drawCircle(drawRect.centerX(), drawRect.centerY(), drawRect.width() / 2, drawPaint)
        }
    }

}