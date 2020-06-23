package com.coin.samplecoin.widget

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Style
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import me.dm7.barcodescanner.core.DisplayUtils
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.core.ViewFinderView
import kotlin.math.max
import kotlin.math.min

class FinderView : ViewFinderView, IViewFinder {

    companion object {
        var MASK_COLOR = android.R.color.transparent
        var BORDER_COLOR = android.R.color.black
    }

    private var framingRect: Rect? = null

    private val laserPaint = Paint()

    private val finderMaskPaint = Paint()

    private val borderPaint = Paint()

    private val lineLength: Float = 140f

    private val cornersSize = 24f

    private val cornersRad = 100f

    private var realWidth: Int = 0

    private var realHeight: Int = 0

    constructor(context: Context) : super(context) {
        this.realWidth = realWidth
        this.realHeight = realHeight
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.init()
    }

    private fun init() {
        laserPaint.apply {
            style = Style.FILL
        }
        finderMaskPaint.apply {
            color = ContextCompat.getColor(context, MASK_COLOR)
        }
        borderPaint.apply {
            color = ContextCompat.getColor(context, BORDER_COLOR)
            style = Style.FILL_AND_STROKE
            strokeWidth = 28f
        }
    }

    override fun setupViewFinder() {
        this.updateFramingRect()
        this.invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        if (this.framingRect != null) {
            this.drawViewFinderMask(canvas)
            this.drawViewFinderBorder(canvas)
        }
    }

    override fun drawViewFinderMask(canvas: Canvas) {
        val width = canvas.width
        val height = canvas.height
        val framingRect = this.framingRect
        canvas.drawRect(
            0.0f,
            0.0f,
            width.toFloat(),
            framingRect!!.top.toFloat(),
            finderMaskPaint
        )
        canvas.drawRect(
            0.0f,
            framingRect.top.toFloat(),
            framingRect.left.toFloat(),
            framingRect.bottom + 1f,
            finderMaskPaint
        )
        canvas.drawRect(
            framingRect.right + 1f,
            framingRect.top.toFloat(),
            width.toFloat(),
            framingRect.bottom + 1f,
            finderMaskPaint
        )
        canvas.drawRect(
            0.0f,
            framingRect.bottom + 1f,
            width.toFloat(),
            height.toFloat(),
            finderMaskPaint
        )
    }

    override fun drawViewFinderBorder(canvas: Canvas) {

        val frame = framingRect ?: return
        val top = frame.top.toFloat()
        val left = frame.left.toFloat()
        val right = frame.right.toFloat()
        val bottom = frame.bottom.toFloat()

        val normalizedRadius = min(cornersRad, max(cornersSize - 1, 0f))

        Path().apply {
            reset()
            moveTo(left, top + cornersSize)
            quadTo(left, top, left + normalizedRadius, top)

            moveTo(right - cornersSize, top)
            quadTo(right, top, right, top + normalizedRadius)

            moveTo(right, bottom - cornersSize)
            quadTo(right, bottom, right - normalizedRadius, bottom)

            moveTo(left + cornersSize, bottom)
            quadTo(left, bottom, left, bottom - normalizedRadius)

            canvas.drawPath(this, borderPaint)
        }

        canvas.drawLine(
            frame.left.toFloat(),
            frame.top + cornersSize - 1f,
            frame.left.toFloat(),
            frame.top + lineLength,
            borderPaint
        )

        canvas.drawLine(
            frame.left + cornersSize - 1f,
            frame.top - 1f,
            frame.left + lineLength,
            frame.top - 1f,
            borderPaint
        )

        canvas.drawLine(
            frame.left.toFloat(),
            frame.bottom - cornersSize + 1f,
            frame.left.toFloat(),
            frame.bottom + 1 - lineLength,
            borderPaint
        )

        canvas.drawLine(
            frame.left.toFloat() + cornersSize - 1f,
            frame.bottom.toFloat(),
            frame.left + lineLength,
            frame.bottom.toFloat(),
            borderPaint
        )

        canvas.drawLine(
            frame.right.toFloat(),
            frame.top + cornersSize - 1f,
            frame.right.toFloat(),
            frame.top + lineLength,
            borderPaint
        )

        canvas.drawLine(
            frame.right - cornersSize + 1f,
            frame.top.toFloat(),
            frame.right - lineLength,
            frame.top.toFloat(),
            borderPaint
        )

        canvas.drawLine(
            frame.right.toFloat(),
            frame.bottom - cornersSize + 1f,
            frame.right.toFloat(),
            frame.bottom - lineLength,
            borderPaint
        )

        canvas.drawLine(
            frame.right - cornersSize + 1f,
            frame.bottom.toFloat(),
            frame.right - lineLength,
            frame.bottom.toFloat(),
            borderPaint
        )
    }

    override fun onSizeChanged(xNew: Int, yNew: Int, xOld: Int, yOld: Int) {
        this.updateFramingRect()
    }

    override fun getFramingRect(): Rect {
        return framingRect!!
    }

    override fun updateFramingRect() {

        val viewResolution = Point(this.width, this.height)
        val orientation = DisplayUtils.getScreenOrientation(this.context)

        var width: Int
        var height: Int

        if (orientation != 1) {
            height = (this.height.toFloat() * 0.625f).toInt()
            width = height
        } else {
            width = (this.width.toFloat() * 0.625f).toInt()
            height = width
        }

        if (width > this.width) width = this.width - 50

        if (height > this.height) height = this.height - 50

        val leftOffset = (viewResolution.x - width) / 2

        val topOffset = (viewResolution.y - height - this.height / 6) / 2

        framingRect = Rect(leftOffset, topOffset, leftOffset + width, topOffset + height)

    }

}
