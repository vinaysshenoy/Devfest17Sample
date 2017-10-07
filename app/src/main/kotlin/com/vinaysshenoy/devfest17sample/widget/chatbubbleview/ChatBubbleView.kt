package com.vinaysshenoy.devfest17sample.widget.chatbubbleview

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.vinaysshenoy.devfest17sample.R
import java.util.logging.Logger

/**
 * Created by vinaysshenoy on 06/09/17.
 */
class ChatBubbleView : View {

  companion object {
    val LOGGER = Logger.getLogger("ChatBubbleView")!!
    val DEBUG_BOUNDS = false
  }

  private val drawRect = RectF()

  private lateinit var bitmapShader: BitmapShader
  private lateinit var drawPaint: Paint
  private lateinit var bitmap: Bitmap
  private lateinit var boundsPaint: Paint

  private lateinit var textPaint: TextPaint
  private var bubbleToTextMargin = 0F
  private var cornerRadius = 0F

  private val lineBounds = Rect()
  private val bubbleBounds = RectF()
  private val bubblePath = Path()
  private val bubbleTrianglePath = Path()
  private val cacheRect = RectF()

  var text: String = ""
    set(value) {
      field = value
      updateTextLayout()
      post({
        requestLayout()
      })
    }

  fun updateTextLayout() {
    textLayout = StaticLayout(
        text,
        textPaint,
        Math.min(width * 0.6F, StaticLayout.getDesiredWidth(text, textPaint)).toInt(),
        Layout.Alignment.ALIGN_NORMAL, 1F, 0F, false)
  }

  private lateinit var textLayout: StaticLayout

  constructor(context: Context?) : super(context) {
    init(context!!, null)
  }

  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    init(context!!, attrs)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

    val w = View.resolveSizeAndState(
        paddingStart + paddingEnd + suggestedMinimumWidth, widthMeasureSpec, 1)

    var h = 0

    for (lineNumber in 0 until textLayout.lineCount) {
      textLayout.getLineBounds(lineNumber, lineBounds)
      h += lineBounds.height()
    }

    setMeasuredDimension(w, h + paddingTop + paddingBottom + (2 * bubbleToTextMargin).toInt())
  }

  private fun init(context: Context, attrs: AttributeSet?) {

    if (!isInEditMode) {
      bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.chat_background_4)
      bitmapShader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    }

    drawPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    drawPaint.style = Paint.Style.FILL
    drawPaint.alpha = 192
    drawPaint.shader = bitmapShader

    boundsPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    boundsPaint.style = Paint.Style.STROKE
    boundsPaint.strokeWidth = 1F * Resources.getSystem().displayMetrics.density
    boundsPaint.color = Color.DKGRAY

    textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG or Paint.SUBPIXEL_TEXT_FLAG)
    textPaint.hinting = Paint.HINTING_ON
    textPaint.style = Paint.Style.FILL
    textPaint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20F,
        Resources.getSystem().displayMetrics)
    textPaint.color = Color.BLACK

    bubbleToTextMargin = 16F * Resources.getSystem().displayMetrics.density
    cornerRadius = 4F * Resources.getSystem().displayMetrics.density

  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    if (w > 0 && h > 0) {

      drawRect.set(0F, 0F, w.toFloat(), h.toFloat())
      drawRect.left += paddingStart
      drawRect.right -= paddingEnd
      drawRect.top += paddingTop
      drawRect.bottom -= paddingBottom

      val left = paddingStart.toFloat()
      val top = paddingTop.toFloat()
      val right = left + textLayout.width.toFloat()
      val bottom = top + textLayout.height.toFloat()
      bubbleBounds.set(left, top, right, bottom)

      bubbleBounds.right += bubbleToTextMargin * 2
      bubbleBounds.bottom += bubbleToTextMargin * 2

      bubblePath.reset()
      bubbleTrianglePath.reset()

      cacheRect.set(bubbleBounds)
      cacheRect.inset(bubbleBounds.width() * 0.04F, bubbleBounds.width() * 0.04F)
      bubblePath.addRoundRect(cacheRect, cornerRadius, cornerRadius, Path.Direction.CW)

      val edgeSize = bubbleBounds.width() * 0.08F
      cacheRect.set(bubbleBounds.left, bubbleBounds.bottom - edgeSize,
          bubbleBounds.left + edgeSize, bubbleBounds.bottom)

      bubbleTrianglePath.moveTo(cacheRect.centerX(), cacheRect.top)
      bubbleTrianglePath.lineTo(cacheRect.left, cacheRect.bottom)
      bubbleTrianglePath.lineTo(cacheRect.right, cacheRect.centerY())
      bubbleTrianglePath.lineTo(cacheRect.centerX(), cacheRect.top)

      bubblePath.op(bubbleTrianglePath, Path.Op.UNION)

    }
    super.onSizeChanged(w, h, oldw, oldh)
    post({
      updateTextLayout()
    })
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    canvas.drawPath(bubblePath, drawPaint)
    val saveCount = canvas.save()
    canvas.translate(
        paddingLeft.toFloat() + bubbleToTextMargin,
        paddingTop.toFloat() + bubbleToTextMargin)
    textLayout.draw(canvas)
    canvas.restoreToCount(saveCount)
  }
}