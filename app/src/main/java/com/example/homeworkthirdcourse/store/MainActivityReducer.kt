package com.example.homeworkthirdcourse.store

import com.example.homeworkthirdcourse.store.MainActivityAction.*

class MainActivityReducer {

    fun reduce(state: MainActivityState, action: MainActivityAction): MainActivityState {
        return when (action) {
            is CalculateStarted -> state.copy(isCalculateLoading = true)
            is CalculateSuccess -> state.copy(isCalculateLoading = false, firstField = action.fieldFirst, secondField = action.fieldSecond, thirdField = action.fieldThird)
            is Calculate -> state.copy(firstField = null, secondField = null, thirdField = null)
            is CalculateError -> state.copy(isCalculateLoading = false)
        }
    }
}
