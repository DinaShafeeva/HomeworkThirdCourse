package com.example.homeworkthirdcourse

class CalculateApi {

    fun writeFirst(second: Int, third: Int): String {
        return calculateFirst(second, third)
    }
    fun writeSecond(first: Int, third: Int): String {
        return calculateSecond(first, third)
    }
    fun writeThird(first: Int, second: Int): String {
        return calculateThird(first, second)
    }

    fun calculateFirst(second: Int, third: Int) = (third - second).toString()
    fun calculateSecond(first: Int, third: Int) = (third - first).toString()
    fun calculateThird(first: Int, second: Int) = (first + second).toString()
}
