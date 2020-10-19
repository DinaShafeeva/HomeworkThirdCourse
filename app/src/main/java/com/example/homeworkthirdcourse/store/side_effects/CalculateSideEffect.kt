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
    private var oldMap: MutableMap<Int, String> = mutableMapOf(0 to "0", 1 to "0", 2 to "0")
    private var index: Int = -1
    private var preIndex = -1

    override fun invoke(
        actions: Observable<MainActivityAction>,
        state: StateAccessor<MainActivityState>
    ): Observable<out MainActivityAction> {
        return actions.ofType<MainActivityAction.Calculate>()
            .switchMap { action ->
                getField(action.field, action.index)
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

    private fun getField(string: String, int: Int): Single<MutableMap<Int, String>> {
//первый элемент
        if (int == 0) {
            oldMap[0] = string
            return when (index){
                0 -> {
                    if (preIndex == 1){
                        oldMap[2] = calculateApi.writeThird((string).toInt(), (oldMap[1] ?: error("0")).toInt())
                    } else {
                        oldMap[1] =
                            calculateApi.writeSecond(string.toInt(), (oldMap[2] ?: error("0")).toInt())
                    }
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
                1 -> {
                    oldMap[2] =
                        calculateApi.writeThird(string.toInt(), (oldMap[1] ?: error("0")).toInt())
                    preIndex = index
                    index = 0
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
                2 -> {
                    oldMap[1] =
                        calculateApi.writeSecond(string.toInt(), (oldMap[2] ?: error("0")).toInt())
                    preIndex = index
                    index = 0
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
                else -> {
                    oldMap[0] = string
                    oldMap[2] =
                        calculateApi.writeThird(string.toInt(), (oldMap[1] ?: error("0")).toInt())
                    index = 0
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
            }
//второй элемент
        } else if (int == 1) {
            oldMap[1] = string
            return when (index){
                1 -> {
                    if (preIndex == 2){
                        oldMap[0] =
                            calculateApi.writeFirst(string.toInt(), (oldMap[2] ?: error("0")).toInt())
                    } else {
                        oldMap[2] =
                            calculateApi.writeThird(string.toInt(), (oldMap[1] ?: error("0")).toInt())
                    }
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
                2 -> {
                    oldMap[0] =
                        calculateApi.writeFirst(string.toInt(), (oldMap[2] ?: error("0")).toInt())
                    preIndex = index
                    index = 1
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
                0 -> {
                    oldMap[2] =
                        calculateApi.writeThird(string.toInt(), (oldMap[1] ?: error("0")).toInt())
                    preIndex = index
                    index = 1
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
                else -> {
                    oldMap[1] = string
                    oldMap[2] =
                        calculateApi.writeThird(string.toInt(), (oldMap[1] ?: error("0")).toInt())
                    index = 1
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
            }
        } else {
            oldMap[2] = string
            return when (index){
                2 -> {
                    if (preIndex == 0){
                        oldMap[1] =
                            calculateApi.writeSecond(string.toInt(), (oldMap[2] ?: error("0")).toInt())
                    } else {
                        oldMap[0] =
                            calculateApi.writeFirst(string.toInt(), (oldMap[2] ?: error("0")).toInt())
                    }
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
                0 -> {
                    oldMap[1] =
                        calculateApi.writeSecond(string.toInt(), (oldMap[2] ?: error("0")).toInt())
                    preIndex = index
                    index = 2
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
                1 -> {
                    oldMap[0] =
                        calculateApi.writeFirst(string.toInt(), (oldMap[2] ?: error("0")).toInt())
                    preIndex = index
                    index = 2
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
                else -> {
                    oldMap[2] = string
                    oldMap[1] =
                        calculateApi.writeFirst(string.toInt(), (oldMap[2] ?: error("0")).toInt())
                    index = 2
                    Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }
            }
        }
    }
}
