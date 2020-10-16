package com.example.homeworkthirdcourse.store

sealed class MainActivityNews {
    class ShowComputationError(val error: String) : MainActivityNews()
}
