package com.example.homeworkthirdcourse.store

data class MainActivityState (
    val isCalculateLoading: Boolean = false,
    var firstField: String? = null,
    var secondField: String? = null,
    var thirdField: String? = null,
    var index: Int = -1,
    var preIndex: Int = -1
)
