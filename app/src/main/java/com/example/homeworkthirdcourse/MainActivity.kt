package com.example.homeworkthirdcourse

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var action: Int? = 0
    var firstNumber: Int = 0
    var secondNumber: Int = 0
    private lateinit var themeSharedPreferences: ThemeSharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDefaultNightMode(themeSharedPreferences.appTheme)
        btn_change_theme.setOnClickListener{
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeSharedPreferences.appTheme = AppCompatDelegate.MODE_NIGHT_NO
            } else {
                setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeSharedPreferences.appTheme = AppCompatDelegate.MODE_NIGHT_YES
            }
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
                    4 -> tv_result.text = divideCall(firstNumber, secondNumber)
                }
                action = 0
                if (tv_result.text.toString() != "На 0 делить нельзя") {
                    firstNumber = Integer.parseInt(tv_result.text.toString())
                } else firstNumber = 0
            }
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        themeSharedPreferences = ThemeSharedPreference(context)
        return super.onCreateView(name, context, attrs)
    }

    private fun plusCall(firstNumber: Int, secondNumber: Int) = firstNumber.plus(secondNumber)
    private fun minusCall(firstNumber: Int, secondNumber: Int) = firstNumber.minus(secondNumber)
    private fun multiplyCall(firstNumber: Int, secondNumber: Int) = firstNumber * secondNumber
    private fun divideCall(firstNumber: Int, secondNumber: Int):String {
        if (secondNumber != 0) {
            return (firstNumber / secondNumber).toString()
        } else return "На 0 делить нельзя"

    }
}
