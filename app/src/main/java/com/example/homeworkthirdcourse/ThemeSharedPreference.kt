package com.example.homeworkthirdcourse

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class ThemeSharedPreference(
    context: Context
) {
    companion object {
        private const val SHARED_PREFERENCE_NAME_APP_THEME = "HomeworkApp"
        private const val KEY_THEME = "Theme"
        private const val DEFAULT_THEME_ID = AppCompatDelegate.MODE_NIGHT_NO
    }

    private val sharedPreference =
        context.getSharedPreferences(SHARED_PREFERENCE_NAME_APP_THEME, Context.MODE_PRIVATE)

    var appTheme: Int
        get() {
            return sharedPreference.getInt(
                KEY_THEME,
                DEFAULT_THEME_ID
            )
        }
        set(value) {
            sharedPreference.edit().putInt(KEY_THEME, value).apply()
        }
}