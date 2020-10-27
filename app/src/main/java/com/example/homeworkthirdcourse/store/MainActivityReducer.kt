package com.example.homeworkthirdcourse.store

import com.example.homeworkthirdcourse.store.MainActivityAction.*

class MainActivityReducer {

    fun reduce(state: MainActivityState, action: MainActivityAction): MainActivityState {
        return when (action) {
            is CalculateStarted -> state.copy(isCalculateLoading = true)
            is CalculateSuccess -> state.copy(
                isCalculateLoading = false,
                firstField = action.fieldFirst,
                secondField = action.fieldSecond,
                thirdField = action.fieldThird
            )
            is Calculate -> {
                state.apply {
                    when (action.index) {
                        1 -> {
                            firstField = action.field
                        }
                        2 -> {
                            secondField = action.field
                        }
                        3 -> {
                            thirdField = action.field
                        }
                    }
                    if (index != action.index) {
                        preIndex = index
                        index = action.index
                    }
                        state.copy(firstField = firstField, secondField = secondField, thirdField = thirdField, index = index, preIndex = preIndex)
                }
            }
            is CalculateError -> state.copy(isCalculateLoading = false)
        }
    }
}
