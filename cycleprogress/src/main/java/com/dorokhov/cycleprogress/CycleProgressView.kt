package com.dorokhov.cycleprogress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

class CycleProgressView
constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0)

    private val paintColor = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundAreaColorUnchecked = context.getColor(R.color.uncheckedBackground)
    private val backgroundAreaColorFailure = context.getColor(R.color.failureBackground)
    private val backgroundAreaColorComplete = context.getColor(R.color.fullCompleteBackground)
    private val backgroundAreaColorNotFullComplete =
        context.getColor(R.color.notFullCompleteBackground)

    var crossDrawable: Drawable
    var checkDrawable: Drawable

    val scale = resources.displayMetrics.density
    var sizeInDp: Float = 20 * scale
    // 0 - unchecked
    // 1 - completed
    // 2 - notFullCompleted
    // 3 - failure
    var typeChecked: Int = UNCHECKED
        set(value) {
            field = value
            invalidate()
        }

    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.CycleProgressView, 0, 0
        ).apply {
            try {
                sizeInDp = getDimension(R.styleable.CycleProgressView_sizeInDp, 0f)
                typeChecked = getInt(R.styleable.CycleProgressView_checkType, 0)
            } finally {
                recycle()
            }
        }
        crossDrawable = context.resources.getDrawable(R.drawable.ic_failure, null)
        checkDrawable = context.resources.getDrawable(R.drawable.ic_done, null)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paintColor.style = Paint.Style.FILL
        val rectBackground = Rect(0, 0, (sizeInDp).toInt(), (sizeInDp).toInt())
        when (typeChecked) {
            UNCHECKED -> {
                paintColor.color = backgroundAreaColorUnchecked
                canvas?.drawRect(rectBackground, paintColor)
            }
            COMPLETED -> {
                paintColor.color = backgroundAreaColorComplete
                canvas?.drawRect(rectBackground, paintColor)
                drawCheck(canvas)
            }
            NOT_FULL_COMPLETED -> {
                paintColor.color = backgroundAreaColorNotFullComplete
                canvas?.drawRect(rectBackground, paintColor)
                drawCheck(canvas)
            }
            FAILURE -> {
                paintColor.color = backgroundAreaColorFailure
                canvas?.drawRect(rectBackground, paintColor)
                drawCross(canvas)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth + sizeInDp.toInt()
        val w: Int = View.resolveSizeAndState(minw, widthMeasureSpec, 0)
        val minh: Int = View.MeasureSpec.getSize(w) + paddingBottom + paddingTop + sizeInDp.toInt()
        val h: Int = View.resolveSizeAndState(
            View.MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        setMeasuredDimension(w, h)
    }

    private fun drawCross(canvas: Canvas?) {
        val scale = resources.displayMetrics.density
        crossDrawable.bounds = Rect(0, 0, (20 * scale).toInt(), (20 * scale).toInt())
        crossDrawable.draw(canvas!!)
    }

    private fun drawCheck(canvas: Canvas?) {
        val scale = resources.displayMetrics.density
        checkDrawable.bounds = Rect(0, 0, (20 * scale).toInt(), (20 * scale).toInt())
        checkDrawable.draw(canvas!!)
    }

    companion object {
        const val UNCHECKED = 0
        const val COMPLETED = 1
        const val NOT_FULL_COMPLETED = 2
        const val FAILURE = 3
    }
}