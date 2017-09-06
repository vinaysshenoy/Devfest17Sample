package com.vinaysshenoy.devfest17sample.widget.backgroundtextureview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.vinaysshenoy.devfest17sample.R

/**
 * Created by vinaysshenoy on 06/09/17.
 */
class BackgroundTextureView : View {

    private val drawRect = RectF()

    private lateinit var bitmapShader: BitmapShader
    private lateinit var drawPaint: Paint
    private lateinit var bitmap: Bitmap

    constructor(context: Context?) : super(context) {
        init(context!!, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context!!, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        if (!isInEditMode) {
            bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.texture)
            bitmapShader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
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
        }
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawRect(drawRect, drawPaint)
    }
}