package com.lambdaschool.readinglist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.lambdaschool.readinglist.R
import com.lambdaschool.readinglist.model.Book

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val NEW_ENTRY_REQUEST = 0
        const val EDIT_ENTRY_REQUEST = 1
    }

    private var entryList = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btn_add.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, EditBookActivity::class.java)
            intent.putExtra(Book.TAG, ll_list!!.childCount.toString())
            startActivityForResult(intent, NEW_ENTRY_REQUEST)
        }
    }

    private fun buildItemView(entry: Book): TextView {
        val view = TextView(this@MainActivity)
        view.text = entry.title
        view.setPadding(15, 15, 15, 15)
        view.textSize = 24f
        view.setOnClickListener {
            val viewEditBookIntent = Intent(this@MainActivity, EditBookActivity::class.java)
            viewEditBookIntent.putExtra(Book.TAG, entry)
            startActivityForResult(viewEditBookIntent, EDIT_ENTRY_REQUEST)
        }
        return view
    }
}
