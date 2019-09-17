package com.lambdaschool.readinglist.ui


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.lambdaschool.readinglist.R
import com.lambdaschool.readinglist.model.Book
import com.lambdaschool.readinglist.model.Book.Companion.createBook
import com.lambdaschool.readinglist.prefs

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import timber.log.Timber.i

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
            val newEntryIntent = Intent(this@MainActivity, EditBookActivity::class.java)
            val entry = createBook()
            newEntryIntent.putExtra(Book.TAG, entry)
            startActivityForResult(newEntryIntent, NEW_ENTRY_REQUEST)
        }

        i("onCreate")

//        entryList = prefs.readAllEntries()
    }

    override fun onStart() {
        super.onStart()
        i("onStart")
    }

    override fun onResume() {
        super.onResume()

        i("onResume")

        ll_list.removeAllViews()
        entryList.forEach { entry ->
            ll_list.addView(buildItemView(entry))
        }
    }

    override fun onPause() {
        super.onPause()

        i("onPause")
    }

    override fun onStop() {
        super.onStop()

        i("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        i("onDestroy")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == NEW_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(Book.TAG) as Book
                    entryList.add(entry)
                    prefs.createEntry(entry)
                }
            } else if (requestCode == EDIT_ENTRY_REQUEST) {
                if (data != null) {
                    val entry = data.getSerializableExtra(Book.TAG) as Book
                    entryList[entry.id] = entry
                    prefs.updateEntry(entry)
                }
            }
        }
    }
}
