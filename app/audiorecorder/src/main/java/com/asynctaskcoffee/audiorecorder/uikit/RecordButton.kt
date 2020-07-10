package com.asynctaskcoffee.audiorecorder.uikit

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Chronometer
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import com.asynctaskcoffee.audiorecorder.R


@SuppressLint("InflateParams")
class RecordButton : FrameLayout {

    private var audioDelete: ImageView? = null
    private var imageViewMic: ImageView? = null
    private var countDownCard: CardView? = null
    private var countDownChronometer: Chronometer? = null
    private var animatedLayout: FrameLayout? = null

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
        audioDelete = v.findViewById(R.id.audio_delete)
        imageViewMic = v.findViewById(R.id.image_view_mic)
        countDownCard = v.findViewById(R.id.count_down_card)
        countDownChronometer = v.findViewById(R.id.count_down_chronometer)
        animatedLayout = v.findViewById(R.id.animated_layout)
        animatedLayout?.setOnClickListener {
            val scaleAnimation = ScaleAnimation(
                1f,
                2f,
                1f,
                2f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            )
            scaleAnimation.duration = 500
            scaleAnimation.fillAfter = true
            animatedLayout?.startAnimation(scaleAnimation)
            scaleAnimation.setAnimationListener(
                object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        countDownCard?.visibility = View.VISIBLE
                        countDownChronometer?.start()
                    }

                    override fun onAnimationStart(animation: Animation?) {

                    }
                }
            )
        }
    }
}