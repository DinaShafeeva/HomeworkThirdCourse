package com.example.homeworkthirdcourse.store

sealed class MainActivityNews {
//    class ShowFirstResult(val result: String): MainActivityNews()
//    class ShowSecondResult(val result: String): MainActivityNews()
//    class ShowThirdResult(val result: String): MainActivityNews()
    class ShowComputationError(val error: String) : MainActivityNews()
}
