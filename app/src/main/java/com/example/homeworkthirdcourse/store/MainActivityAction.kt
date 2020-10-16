package com.example.homeworkthirdcourse.store

sealed class MainActivityAction {

    class Calculate(val map: MutableMap <Int, String>): MainActivityAction()
    //три экшена

    class CalculateSuccess(val fieldFirst: String?, val fieldSecond: String?, val fieldThird: String?): MainActivityAction()

    object CalculateError: MainActivityAction()

    object CalculateStarted: MainActivityAction()
}
