package com.lambdaschool.sharedprefsdemo

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TIMES_PRESSED_KEY = "Times Pressed"
    }
    var timesPressed = 0

    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE)

        timesPressed = prefs?.getInt(TIMES_PRESSED_KEY, 0) ?: 0
        value.text = "$timesPressed"

        button.setOnClickListener {
            timesPressed++
            value.text = "$timesPressed"

            prefs?.let {
                val editor = it.edit()
                editor.putInt(TIMES_PRESSED_KEY, timesPressed)
                editor.commit()
            }
        }
    }
}
