package com.asynctaskcoffee.audiorecorder.uikit

import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.SystemClock
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Chronometer
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import com.asynctaskcoffee.audiorecorder.R


@SuppressLint("ClickableViewAccessibility,InflateParams")
class RecordButton : FrameLayout {

    private var imageViewMic: ImageView? = null
    private var countDownCard: FrameLayout? = null
    private var countDownChronometer: Chronometer? = null
    private var animatedLayout: FrameLayout? = null

    var marginIn30Dp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 30f, resources
            .displayMetrics
    ).toInt()

    var marginIn5Dp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 5f, resources
            .displayMetrics
    ).toInt()

    var beepEnabled = true

    constructor(context: Context) : super(context) {
        setupVisualComponents(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        setupVisualComponents(context)
    }

    private fun setupVisualComponents(context: Context) {
        val v = LayoutInflater.from(context).inflate(R.layout.record_button_layout, null)
        addView(v)
        setViews(v)
    }

    private fun setViews(v: View) {
        imageViewMic = v.findViewById(R.id.image_view_mic)
        countDownCard = v.findViewById(R.id.count_down_card)
        countDownChronometer = v.findViewById(R.id.count_down_chronometer)
        animatedLayout = v.findViewById(R.id.animated_layout)
        animatedLayout?.setOnTouchListener(
            object : OnTouchListener {
                override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                    when (p1?.action) {
                        MotionEvent.ACTION_DOWN -> {
                            val scaleAnimation = ScaleAnimation(
                                1f,
                                4.5f,
                                1f,
                                4.5f,
                                Animation.RELATIVE_TO_SELF,
                                0.5f,
                                Animation.RELATIVE_TO_SELF,
                                0.5f
                            )
                            scaleAnimation.duration = 250
                            scaleAnimation.fillAfter = true
                            animatedLayout?.startAnimation(scaleAnimation)
                            scaleAnimation.setAnimationListener(
                                object : Animation.AnimationListener {
                                    override fun onAnimationRepeat(animation: Animation?) {

                                    }

                                    override fun onAnimationEnd(animation: Animation?) {
                                        countDownCard?.visibility = View.VISIBLE
                                        countDownChronometer?.base = SystemClock.elapsedRealtime()
                                        countDownChronometer?.start()
                                    }

                                    override fun onAnimationStart(animation: Animation?) {
                                        if (beepEnabled)
                                            ToneGenerator(
                                                AudioManager.STREAM_MUSIC,
                                                70
                                            ).startTone(ToneGenerator.TONE_CDMA_PIP, 150)

                                    }
                                }
                            )
                            return true
                        }
                        MotionEvent.ACTION_UP,
                        MotionEvent.ACTION_BUTTON_RELEASE -> {
                            val scaleAnimation = ScaleAnimation(
                                4.5f,
                                1f,
                                4.5f,
                                1f,
                                Animation.RELATIVE_TO_SELF,
                                0.5f,
                                Animation.RELATIVE_TO_SELF,
                                0.5f
                            )
                            scaleAnimation.duration = 250
                            scaleAnimation.fillAfter = true
                            animatedLayout?.startAnimation(scaleAnimation)
                            scaleAnimation.setAnimationListener(
                                object : Animation.AnimationListener {
                                    override fun onAnimationRepeat(animation: Animation?) {

                                    }

                                    override fun onAnimationEnd(animation: Animation?) {
                                        countDownCard?.visibility = View.GONE
                                        countDownChronometer?.stop()
                                    }

                                    override fun onAnimationStart(animation: Animation?) {

                                    }
                                }
                            )
                            return true
                        }
                    }
                    return false
                }
            }
        )
    }
}