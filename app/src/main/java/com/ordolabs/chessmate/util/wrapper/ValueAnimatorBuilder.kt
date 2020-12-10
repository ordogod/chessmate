package com.ordolabs.chessmate.util.wrapper

import android.R.integer.config_shortAnimTime
import android.animation.ValueAnimator
import android.view.animation.Interpolator
import androidx.core.animation.doOnEnd
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.ordolabs.chessmate.ChessMateApp

class ValueAnimatorBuilder<T : Number> private constructor(
    private val isForward: Boolean,
    initializer: ValueAnimatorBuilder<T>.() -> Unit
) {

    companion object {
        fun <T : Number> of(
            isForward: Boolean, initializer: ValueAnimatorBuilder<T>.() -> Unit
        ): ValueAnimator {
            return ValueAnimatorBuilder(isForward, initializer).animator
        }
    }

    private lateinit var values: Array<T>
    private lateinit var animator: ValueAnimator

    private var defaultDuration: Long
    private var defaultInterpolator: Interpolator

    init {
        val resources = ChessMateApp.context.resources
        this.defaultDuration = resources.getInteger(config_shortAnimTime).toLong()
        this.defaultInterpolator = FastOutSlowInInterpolator()
        initializer.invoke(this)
    }

    fun values(setter: () -> Array<T>) {
        this.values = setter()
        this.animator = build()
    }

    fun duration(setter: () -> Long) {
        this.animator.duration = setter()
    }

    fun interpolator(setter: () -> Interpolator) {
        this.animator.interpolator = setter()
    }

    fun updateListener(listener: ValueAnimator.() -> Unit) {
        this.animator.addUpdateListener(listener)
    }

    fun onEnd(action: () -> Unit) {
        this.animator.doOnEnd { action() }
    }

    fun start() {
        this.animator.start()
    }

    private fun build(): ValueAnimator {
        val animator = when (values[0]) {
            is Int -> buildAnimatorOfInt()
            is Float -> buildAnimatorOfFloat()
            else -> throw IllegalArgumentException("ValueAnimators of specified type are not supported")
        }
        animator.duration = this.defaultDuration
        animator.interpolator = this.defaultInterpolator
        return animator
    }

    private fun buildAnimatorOfInt() = if (isForward) {
        ValueAnimator.ofInt(values[0] as Int, values[1] as Int)
    } else {
        ValueAnimator.ofInt(values[1] as Int, values[0] as Int)
    }

    private fun buildAnimatorOfFloat() = if (isForward) {
        ValueAnimator.ofFloat(values[0] as Float, values[1] as Float)
    } else {
        ValueAnimator.ofFloat(values[1] as Float, values[0] as Float)
    }
}