package com.example.homeworkthirdcourse

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animation = AnimationUtils.loadAnimation(this, R.anim.alpha)
        btn_2.startAnimation(animation)
    }

    fun onAnimationStart(animation: Animation?) {
        btn_2.visibility = View.VISIBLE
    }

    fun onAnimationEnd(animation: Animation?) {
        btn_2.visibility = View.INVISIBLE
    }

    fun onAnimationRepeat(animation: Animation?) {
        btn_2.visibility = View.VISIBLE
    }
}