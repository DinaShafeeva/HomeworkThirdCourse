package com.example.homeworkthirdcourse.store

sealed class MainActivityAction {

    class Calculate(val field: String, val index: Int): MainActivityAction()

    class CalculateSuccess(val fieldFirst: String?, val fieldSecond: String?, val fieldThird: String?): MainActivityAction()

    object CalculateError: MainActivityAction()

    object CalculateStarted: MainActivityAction()
}
