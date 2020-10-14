package com.example.homeworkthirdcourse.store.side_effects

import com.example.homeworkthirdcourse.CalculateApi
import com.example.homeworkthirdcourse.store.*
import com.freeletics.rxredux.StateAccessor
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.ofType
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit

class CalculateSideEffect(
    private val calculateApi: CalculateApi,
    private val newsRelay: Relay<MainActivityNews>
) :MainActivitySideEffect {
    private var oldMap: MutableMap<Int, String> = mutableMapOf(0 to "0", 1 to "0", 2 to "0")
    private var index: Int = -1

    override fun invoke(
        actions: Observable<MainActivityAction>,
        state: StateAccessor<MainActivityState>
    ): Observable<out MainActivityAction> {
        return actions.ofType<MainActivityAction.Calculate>()
            .switchMap { action ->
                getField(action.map)
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

    private fun getField(fieldMap: Map<Int, String>): Single<MutableMap<Int, String>> {

        //первый элемент
        if (fieldMap[0] != oldMap[0]) {
            fieldMap[0]?.let { oldMap.put(0, it) }

            if (index == 1) {
                oldMap[2] =
                    calculateApi.writeThird((fieldMap[0] ?: error("0")).toInt(), (fieldMap[1] ?: error("0")).toInt())
                index = 0
                return Single.just(oldMap).delay(5, TimeUnit.SECONDS)

            } else if (index == 2) {
                oldMap[1] =
                    calculateApi.writeSecond((fieldMap[0] ?: error("0")).toInt(), (fieldMap[2] ?: error("0")).toInt())
                index = 0
                return Single.just(oldMap).delay(5, TimeUnit.SECONDS)

            } else {
                fieldMap[0]?.let { oldMap.put(0, it) }
                oldMap[2] =
                    calculateApi.writeThird((fieldMap[0] ?: error("0")).toInt(), (fieldMap[1] ?: error("0")).toInt())
                index = 0
                return Single.just(oldMap).delay(5, TimeUnit.SECONDS)
            }

            //второй элемент
            } else if (fieldMap[1] != oldMap[1]) {
                fieldMap[1]?.let { oldMap.put(1, it) }

                if (index == 2) {
                    oldMap[0] =
                        calculateApi.writeFirst((fieldMap[1] ?: error("0")).toInt(), (fieldMap[2] ?: error("0")).toInt())
                    index = 1
                    return Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                } else if (index == 0) {
                    oldMap[2] =
                        calculateApi.writeThird((fieldMap[0] ?: error("0")).toInt(), (fieldMap[1] ?: error("0")).toInt())
                    index = 1
                    return Single.just(oldMap).delay(5, TimeUnit.SECONDS)

                } else {
                    fieldMap[1]?.let { oldMap.put(1, it) }
                    oldMap[2] =
                        calculateApi.writeThird((fieldMap[0] ?: error("0")).toInt(), (fieldMap[1] ?: error("0")).toInt())
                    index = 1
                    return Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                }

                } else {
                    fieldMap[2]?.let { oldMap.put(2, it) }

                    if (index == 0) {
                        oldMap[1] =
                            calculateApi.writeSecond((fieldMap[0] ?: error("0")).toInt(), (fieldMap[2] ?: error("0")).toInt())
                        index = 2
                        return Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                    } else if (index == 1) {
                        oldMap[0] =
                            calculateApi.writeFirst((fieldMap[1] ?: error("0")).toInt(), (fieldMap[2] ?: error("0")).toInt())
                        index = 2
                        return Single.just(oldMap).delay(5, TimeUnit.SECONDS)

                    } else  {
                        fieldMap[2]?.let { oldMap.put(2, it) }
                        oldMap[1] =
                            calculateApi.writeFirst((fieldMap[1] ?: error("0")).toInt(), (fieldMap[2] ?: error("0")).toInt())
                        index = 2
                        return Single.just(oldMap).delay(5, TimeUnit.SECONDS)
                    }
                }
    }
}
