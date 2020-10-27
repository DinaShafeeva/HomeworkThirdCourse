package com.example.homeworkthirdcourse.store.side_effects

import com.example.homeworkthirdcourse.CalculateApi
import com.example.homeworkthirdcourse.MainActivity
import com.example.homeworkthirdcourse.store.*
import com.example.homeworkthirdcourse.store.side_effects.MainActivitySideEffect
import com.freeletics.rxredux.StateAccessor
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.ofType
import java.lang.Exception
import java.util.concurrent.TimeUnit

class CalculateSideEffect(
    private val calculateApi: CalculateApi,
    private val newsRelay: Relay<MainActivityNews>
) :MainActivitySideEffect {

    override fun invoke(
        actions: Observable<MainActivityAction>,
        state: StateAccessor<MainActivityState>
    ): Observable<out MainActivityAction> {
        return actions.ofType<MainActivityAction.Calculate>()
            .switchMap {
                getField(state())
                    .map<MainActivityAction> {
                        MainActivityAction.CalculateSuccess(
                            it[0],
                            it[1],
                            it[2]
                        )
                    }
                    .doOnError { throwable ->
                        when (throwable) {
                            Exception("Only numbers are allowed") -> newsRelay.accept(
                                MainActivityNews.ShowComputationError(
                                    throwable.message.toString()
                                )
                            )
                        }
                    }
                    .onErrorReturnItem(MainActivityAction.CalculateError)
                    .toObservable()
                    .startWith(MainActivityAction.CalculateStarted)
            }
    }


    private fun getField(state: MainActivityState): Single<List<String>> {
        var firstField: Int = state.firstField?.toInt() ?: 0
        var secondField: Int = state.secondField?.toInt() ?: 0
        var thirdField: Int = state.thirdField?.toInt() ?: 0
        var index: Int = state.index
        var preIndex: Int = state.preIndex
        var newValue: Int
        lateinit var list: List<String>
        if (index == 1) {
            if (preIndex == 2) {
                newValue = calculateApi.writeThird(firstField, secondField).toInt()
                list = listOf<String>(firstField.toString(), secondField.toString(),newValue.toString())
            } else if (preIndex == 3) {
                newValue = calculateApi.writeSecond(firstField, thirdField).toInt()
                list = listOf<String>(firstField.toString(), newValue.toString(),thirdField.toString())
            } else if (preIndex == -1) {
                newValue = calculateApi.writeThird(firstField, 0).toInt()
                list = listOf<String>(firstField.toString(), secondField.toString(),newValue.toString())

            }
        } else if (index == 2) {
            if (preIndex == 1) {
                newValue = calculateApi.writeThird(firstField, secondField).toInt()
                list = listOf<String>(firstField.toString(), secondField.toString(),newValue.toString())
            } else if (preIndex == 3) {
                newValue = calculateApi.writeFirst(secondField, thirdField).toInt()
                list = listOf<String>(newValue.toString(), secondField.toString(),thirdField.toString())
            } else if (preIndex == -1) {
                newValue = calculateApi.writeThird(0, secondField).toInt()
                list = listOf<String>(firstField.toString(), secondField.toString(),newValue.toString())
            }
        } else {
            if (preIndex == 1) {
                newValue = calculateApi.writeSecond(firstField, thirdField).toInt()
                list = listOf<String>(firstField.toString(), newValue.toString(),thirdField.toString())
            } else if (preIndex == 2) {
                newValue = calculateApi.writeFirst(secondField, thirdField).toInt()
                list = listOf<String>(newValue.toString(), secondField.toString(),thirdField.toString())
            } else if (preIndex == -1) {
                newValue = calculateApi.writeFirst(0, thirdField).toInt()
                list = listOf<String>(newValue.toString(), secondField.toString(),thirdField.toString())
            }
        }
        return Single.just(list).delay(5, TimeUnit.SECONDS)
    }
}

