package com.lambdaschool.readinglist.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.lambdaschool.readinglist.R
import com.lambdaschool.readinglist.model.Book

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btn_add.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun buildItemView(entry: Book): TextView {
        val view = TextView(this@MainActivity)
        view.text = entry.title
        view.setPadding(15, 15, 15, 15)
        view.textSize = 24f
        return view
    }
}
