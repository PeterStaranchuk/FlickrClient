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
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnRepeat
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

    private val loadingCircleSize by lazy { resources.getDimension(R.dimen.loading_circle_size) }
    private val loadingCircleMargin by lazy { resources.getDimension(R.dimen.loading_circle_margin) }

    private val loadingBlue by lazy { ContextCompat.getColor(context, R.color.flickr_blue) }
    private val loadingPurple by lazy { ContextCompat.getColor(context, R.color.flickr_purple) }

    private var textAlphaAnimator: ValueAnimator? = null
    private var state = State.NORMAL
    private var offset = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    enum class State {
        NORMAL,
        LOADING
    }

    private val loadingCirclePaint by lazy {
        Paint().apply {
            isAntiAlias = true
        }
    }

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
        when(state) {
            State.NORMAL -> {
                canvas?.drawLine(0f, 0f, measuredWidth.toFloat(), 0f, borderPaint)
                canvas?.drawLine(measuredWidth.toFloat(), 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), borderPaint)
                canvas?.drawLine(0f, measuredHeight.toFloat(), measuredWidth.toFloat(), measuredHeight.toFloat(), borderPaint)
                canvas?.drawLine(0f, 0f, 0f, measuredHeight.toFloat(), borderPaint)
                canvas?.drawText(buttonText, (measuredWidth - textPaint.measureText(buttonText)) / 2f, (measuredHeight - textPaint.fontMetrics.top) / 2f, textPaint)
            }
            State.LOADING -> {
                loadingCirclePaint.color = if(isRevert) loadingPurple else loadingBlue
                canvas?.drawCircle(
                    measuredWidth / 2f - loadingCircleSize / 2f - loadingCircleMargin / 2f + offset,
                    measuredHeight / 2f - loadingCircleSize / 2f,
                    loadingCircleSize / 2f,
                    loadingCirclePaint
                )

                loadingCirclePaint.color = if(isRevert) loadingBlue else loadingPurple
                canvas?.drawCircle(
                    measuredWidth / 2f + loadingCircleSize / 2f + loadingCircleMargin / 2f - offset,
                    measuredHeight / 2f - loadingCircleSize / 2f,
                    loadingCircleSize / 2f,
                    loadingCirclePaint
                )
            }
        }
    }

    var isRevert = false

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun connectListener() {
        setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    textAlphaAnimator?.cancel()
                    textPaint.alpha = 64
                    isClickable = false
                    invalidate()

                    postDelayed({
                        state = State.LOADING
                        ValueAnimator.ofFloat(0f, loadingCircleSize + loadingCircleMargin).apply {
                            addUpdateListener {
                                offset = it.animatedValue as Float
                                postInvalidate()
                            }
                            doOnRepeat {
                                isRevert = !isRevert
                            }
                            interpolator = AccelerateDecelerateInterpolator()
                            repeatMode = ValueAnimator.RESTART
                            repeatCount = ValueAnimator.INFINITE
                            duration = 1000
                            start()
                        }
                        postInvalidate()
                    }, 300)
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
                    performClick()
                    true
                }
                else -> false
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disconnectListener() {
        setOnTouchListener(null)
    }

}