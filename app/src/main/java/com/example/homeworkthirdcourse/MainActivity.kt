package com.example.homeworkthirdcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.homeworkthirdcourse.store.MainActivityAction
import com.example.homeworkthirdcourse.store.MainActivityNews
import com.example.homeworkthirdcourse.store.MainActivityState
import com.example.homeworkthirdcourse.store.MainActivityStore
import com.example.homeworkthirdcourse.store.side_effects.CalculateSideEffect
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        var TAG = ""
        const val TAG_EMPTY = "EMPTY"
        const val TAG_SYSTEM = "SYSTEM"
    }
    private val relay = PublishRelay.create<MainActivityNews>()

    private val store = MainActivityStore(
        listOf(
            CalculateSideEffect(CalculateApi(), relay)),
            relay
        )

    var map: MutableMap<Int, String> = mutableMapOf(0 to "0", 1 to "0", 2 to "0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        store.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::render)

        store.newsRelay
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::renderNews)

        setupTextChangedListeners()
    }

    private fun setupTextChangedListeners() {
        et_first.doAfterTextChanged { text ->
            updateValues(0, text.toString())
        }

        et_second.doAfterTextChanged { text ->
            updateValues(1, text.toString())
        }

        et_third.doAfterTextChanged { text ->
            updateValues(2, text.toString())
        }
    }

    private fun updateValues(int: Int, string: String) {
        if (TAG != TAG_SYSTEM && string != "-" && string != "") {
            map[int] = string
            store.actionRelay.onNext(
                MainActivityAction.Calculate(map)
            )
        }
    }

    private fun render(state: MainActivityState) {
        TAG = TAG_SYSTEM
        state.firstField?.let {
            if (et_first.text.toString() != state.firstField && state.firstField != "") {
                et_first.setText(state.firstField)
            }
        }
        state.secondField?.let {
            if (et_second.text.toString() != state.secondField && state.secondField != "") {
                et_second.setText(state.secondField)
            }
        }
        state.thirdField?.let{
            if (et_third.text.toString() != state.thirdField && state.thirdField != "") {
                et_third.setText(state.thirdField)
            }
        }
        TAG = TAG_EMPTY
        progressBar.isVisible = state.isCalculateLoading
    }

    private fun renderNews(news: MainActivityNews) {
        when (news) {
            is MainActivityNews.ShowComputationError -> Toast.makeText(
                this,
                news.error,
                LENGTH_LONG
            ).show()
        }
    }
}
