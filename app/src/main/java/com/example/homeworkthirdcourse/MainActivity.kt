package com.example.homeworkthirdcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var action: Int? = 0
    var firstNumber: Int = 0
    var secondNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_change_theme.setOnClickListener{
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        btn_plus.setOnClickListener{
            action = 1
            if (et.text.isNotEmpty()) {
                firstNumber = Integer.parseInt(et.text.toString())
                et.text.clear()
            }
        }

        btn_minus.setOnClickListener{
            action = 2
            if (et.text.isNotEmpty()) {
                firstNumber = Integer.parseInt(et.text.toString())
                et.text.clear()
            }
        }

        btn_multiply.setOnClickListener{
            action = 3
            if (et.text.isNotEmpty()) {
                firstNumber = Integer.parseInt(et.text.toString())
                et.text.clear()
            }
        }

        btn_divide.setOnClickListener{
            action = 4
            if (et.text.isNotEmpty()) {
                firstNumber = Integer.parseInt(et.text.toString())
                et.text.clear()
            }
        }

        btn_result.setOnClickListener {
            if (action != 0) {
                secondNumber = Integer.parseInt(et.text.toString())
                et.text.clear()
                when (action) {
                    1 -> tv_result.text = plusCall(firstNumber, secondNumber).toString()
                    2 -> tv_result.text = minusCall(firstNumber, secondNumber).toString()
                    3 -> tv_result.text = multiplyCall(firstNumber, secondNumber).toString()
                    4 -> tv_result.text = divideCall(firstNumber, secondNumber).toString()
                }
                action = 0
                firstNumber = Integer.parseInt(tv_result.text.toString())
            }
        }
    }

    private fun plusCall(firstNumber: Int, secondNumber: Int) = firstNumber.plus(secondNumber)
    private fun minusCall(firstNumber: Int, secondNumber: Int) = firstNumber.minus(secondNumber)
    private fun multiplyCall(firstNumber: Int, secondNumber: Int) = firstNumber * secondNumber
    private fun divideCall(firstNumber: Int, secondNumber: Int) = firstNumber / secondNumber
}
