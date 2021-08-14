package com.peterstaranchuk.onboarding.ui.onboarding.custom

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.peterstaranchuk.onboarding.R

class MainActionButton : View, LifecycleObserver {

    private val borderWidth by lazy { resources.getDimension(R.dimen.onboarding_main_button_border_width) }
    private val textSize by lazy { resources.getDimension(R.dimen.onboarding_main_button_text_size) }
    private val borderColor by lazy { ContextCompat.getColor(context, R.color.onboarding_main_button_border_color) }
    private val textColor by lazy { ContextCompat.getColor(context, R.color.onboarding_main_button_text_color) }
    private val buttonText by lazy { resources.getString(R.string.onboarding_main_button_text) }
    var textAlphaAnimator: ValueAnimator? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val textPaint by lazy {
        TextPaint().apply {
            isAntiAlias = true
            textSize = this@MainActionButton.textSize
            color = this@MainActionButton.textColor
            //todo make text bold
            //todo setup font
        }
    }

    private val borderPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = borderColor
            strokeWidth = borderWidth
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawLine(0f, 0f, measuredWidth.toFloat(), 0f, borderPaint)
        canvas?.drawLine(measuredWidth.toFloat(), 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), borderPaint)
        canvas?.drawLine(0f, measuredHeight.toFloat(), measuredWidth.toFloat(), measuredHeight.toFloat(), borderPaint)
        canvas?.drawLine(0f, 0f, 0f, measuredHeight.toFloat(), borderPaint)
        canvas?.drawText(buttonText, (measuredWidth - textPaint.measureText(buttonText)) / 2f, (measuredHeight - textPaint.fontMetrics.top) / 2f, textPaint)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @SuppressLint("ClickableViewAccessibility")
    fun connectListener() {
        setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    textAlphaAnimator?.cancel()
                    textPaint.alpha = 64
                    invalidate()
                    true
                }

                MotionEvent.ACTION_UP -> {
                    textAlphaAnimator = ValueAnimator.ofInt(textPaint.alpha, 255).apply {
                        addUpdateListener {
                            textPaint.alpha = it.animatedValue as Int
                            invalidate()
                        }
                        duration = 700
                        interpolator = AccelerateInterpolator()
                        start()
                    }
                    true
                }
                else -> false
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @SuppressLint("ClickableViewAccessibility")
    fun disconnectListener() {
        setOnTouchListener(null)
    }

}