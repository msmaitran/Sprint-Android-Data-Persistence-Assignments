package com.lambdaschool.readinglist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.lambdaschool.readinglist.R
import com.lambdaschool.readinglist.model.Book
import kotlinx.android.synthetic.main.activity_edit_book.*
import timber.log.Timber.i

class EditBookActivity : AppCompatActivity() {

    private var entry: Book = Book(Book.INVALID_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_book)

        i("onCreate")

        val intent = intent
        entry = intent.getSerializableExtra(Book.TAG) as Book

        et_title.setText(entry.title)
        et_title.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val entryString = s.toString()
                entry.title = entryString
            }
        })
        et_reason.setText(entry.reasonToRead)
        et_reason.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val entryString = s.toString()
                entry.reasonToRead = entryString
            }
        })

        if (entry.hasBeenRead == false) {
            cb_read.isChecked = false
        } else {
            cb_read.isChecked = true
        }

        cb_read.setOnClickListener {
            if (cb_read.isChecked == false) {
                entry.hasBeenRead = false
            } else {
                entry.hasBeenRead = true
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        i("onStart")
    }

    override fun onResume() {
        super.onResume()
        i("onResume")
    }

    // user interacting with app

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                val resultIntent = Intent()
                resultIntent.putExtra(Book.TAG, entry)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
