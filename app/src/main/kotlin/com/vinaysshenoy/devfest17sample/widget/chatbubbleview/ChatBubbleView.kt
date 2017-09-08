package com.vinaysshenoy.devfest17sample.widget.chatbubbleview

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.vinaysshenoy.devfest17sample.R

/**
 * Created by vinaysshenoy on 06/09/17.
 */
class ChatBubbleView : View {

    private val drawRect = RectF()

    private lateinit var bitmapShader: BitmapShader
    private lateinit var drawPaint: Paint
    private lateinit var bitmap: Bitmap

    private lateinit var textPaint: TextPaint

    var text: String = ""
        set(value) {
            field = value
            updateTextLayout()
            requestLayout()
        }

    private lateinit var textLayout: StaticLayout

    fun updateTextLayout() {
        textLayout = StaticLayout(
                text,
                textPaint,
                Math.min(width * 0.4F, StaticLayout.getDesiredWidth(text, textPaint)).toInt(),
                Layout.Alignment.ALIGN_NORMAL, 1.0F, 1.0F, false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val w = View.resolveSizeAndState(paddingLeft + paddingRight + suggestedMinimumWidth, widthMeasureSpec, 1)
        val h = View.resolveSizeAndState(paddingTop + paddingBottom + textLayout!!.height, heightMeasureSpec, 0)
        setMeasuredDimension(w, h)
    }

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

        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.SUBPIXEL_TEXT_FLAG)
        textPaint.hinting = Paint.HINTING_ON
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 24F, Resources.getSystem().displayMetrics)
        textPaint.color = Color.BLACK
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
        updateTextLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        textLayout.draw(canvas)
    }
}