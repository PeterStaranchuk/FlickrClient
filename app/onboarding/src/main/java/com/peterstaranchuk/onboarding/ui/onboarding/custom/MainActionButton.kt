package com.peterstaranchuk.onboarding.ui.onboarding.custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnRepeat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.peterstaranchuk.onboarding.R

class MainActionButton : View, LifecycleObserver {

    private val buttonText by lazy { resources.getString(R.string.onboarding_main_button_text) }
    private val loadingCircleSize by lazy { resources.getDimension(R.dimen.loading_circle_size) }
    private val loadingCircleMargin by lazy { resources.getDimension(R.dimen.loading_circle_margin) }

    private val loadingBlue by lazy { ContextCompat.getColor(context, R.color.flickr_blue) }
    private val loadingPurple by lazy { ContextCompat.getColor(context, R.color.flickr_purple) }

    private var textAlphaAnimator: ValueAnimator? = null
    private var state = ButtonState.DEFAULT
    private var offset = 0f
    private var isReversePartOfAnimation = false
    private var textBounds = Rect()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val loadingCirclePaint by lazy {
        Paint().apply {
            isAntiAlias = true
        }
    }

    private val textPaint by lazy {
        TextPaint().apply {
            isAntiAlias = true
            textSize = resources.getDimension(R.dimen.onboarding_main_button_text_size)
            color =  ContextCompat.getColor(context, R.color.onboarding_main_button_text_color)
            typeface = ResourcesCompat.getFont(context, R.font.nunito_sans_extra_bold)
        }
    }

    private val borderPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.onboarding_main_button_border_color)
            strokeWidth = resources.getDimension(R.dimen.onboarding_main_button_border_width)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        when(state) {
            ButtonState.DEFAULT -> {
                canvas?.drawLine(0f, 0f, measuredWidth.toFloat(), 0f, borderPaint)
                canvas?.drawLine(measuredWidth.toFloat(), 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), borderPaint)
                canvas?.drawLine(0f, measuredHeight.toFloat(), measuredWidth.toFloat(), measuredHeight.toFloat(), borderPaint)
                canvas?.drawLine(0f, 0f, 0f, measuredHeight.toFloat(), borderPaint)

                textPaint.getTextBounds(buttonText, 0, buttonText.length, textBounds)
                canvas?.drawText(buttonText, (measuredWidth - textBounds.width()) / 2f, (measuredHeight + textBounds.height()) / 2f, textPaint)
            }
            ButtonState.LOADING -> {
                loadingCirclePaint.color = if(isReversePartOfAnimation) loadingPurple else loadingBlue
                canvas?.drawCircle(
                    measuredWidth / 2f - loadingCircleSize / 2f - loadingCircleMargin / 2f + offset,
                    measuredHeight / 2f - loadingCircleSize / 2f,
                    loadingCircleSize / 2f,
                    loadingCirclePaint
                )

                loadingCirclePaint.color = if(isReversePartOfAnimation) loadingBlue else loadingPurple
                canvas?.drawCircle(
                    measuredWidth / 2f + loadingCircleSize / 2f + loadingCircleMargin / 2f - offset,
                    measuredHeight / 2f - loadingCircleSize / 2f,
                    loadingCircleSize / 2f,
                    loadingCirclePaint
                )
            }
        }
    }

    var globalVisibleRect = Rect()
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun connectListener() {
        setOnTouchListener { _, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    textAlphaAnimator?.cancel()
                    textPaint.alpha = 64
                    isClickable = false
                    invalidate()
                    true
                }

                MotionEvent.ACTION_UP -> {
                    animateToDefaultTextState()
                    getGlobalVisibleRect(globalVisibleRect)
                    if(globalVisibleRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        performClick()
                    }
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    getGlobalVisibleRect(globalVisibleRect)
                    if(!globalVisibleRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        animateToDefaultTextState()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun animateToDefaultTextState() {
        if(textAlphaAnimator?.isRunning == true) return //animation is already running. No need to cancel it and start the new one

        textAlphaAnimator = ValueAnimator.ofInt(textPaint.alpha, 255).apply {
            addUpdateListener {
                textPaint.alpha = it.animatedValue as Int
                invalidate()
            }
            duration = 400
            interpolator = AccelerateInterpolator()
            start()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disconnectListener() {
        setOnTouchListener(null)
    }

    fun setLoadingState() {
        state = ButtonState.LOADING
        ValueAnimator.ofFloat(0f, loadingCircleSize + loadingCircleMargin).apply {
            addUpdateListener {
                offset = it.animatedValue as Float
                postInvalidate()
            }
            doOnRepeat {
                isReversePartOfAnimation = !isReversePartOfAnimation
            }
            interpolator = AccelerateDecelerateInterpolator()
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            duration = 1000
            start()
        }
        postInvalidate()
    }

    fun setDefaultState() {
        state = ButtonState.DEFAULT
    }
}